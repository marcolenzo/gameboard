package com.marcolenzo.gameboard.api;


import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.api.exceptions.BadRequestException;
import com.marcolenzo.gameboard.api.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.api.services.RatingServices;
import com.marcolenzo.gameboard.commons.comparators.ResistanceGameComparator;
import com.marcolenzo.gameboard.commons.model.Board;
import com.marcolenzo.gameboard.commons.model.BoardStatistics;
import com.marcolenzo.gameboard.commons.model.PlayerStatistics;
import com.marcolenzo.gameboard.commons.model.ResistanceGame;
import com.marcolenzo.gameboard.commons.model.User;
import com.marcolenzo.gameboard.commons.repositories.BoardRepository;
import com.marcolenzo.gameboard.commons.repositories.ResistanceGameRepository;
import com.marcolenzo.gameboard.commons.repositories.UserRepository;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class BoardController {

	@Autowired
	private BoardRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResistanceGameRepository gameRepository;

	@Autowired
	private RatingServices ratingServices;

	@RequestMapping(value = "/api/board", method = RequestMethod.POST)
	public Board createGameboard(@Valid @RequestBody Board gameboard) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Do not accept externally defined IDs.
		gameboard.setId(null);

		// Sanitize player set and make sure current user is present
		boolean isCurrentUserPresent = false;
		for (PlayerStatistics player : gameboard.getPlayers()) {
			if (player.getUserId().equals(currentUser.getId())) {
				isCurrentUserPresent = true;
			}
			player.setElo(1500);
			player.setMatchesPlayed(0);
			player.setMatchesPlayedAsResistance(0);
			player.setMatchesPlayedAsSpy(0);
			player.setMatchesWon(0);
			player.setMatchesWonAsResistance(0);
			player.setMatchesWonAsSpy(0);
			player.setEloVariation(0);
		}

		if (!isCurrentUserPresent) {
			PlayerStatistics player = new PlayerStatistics();
			player.setUserId(currentUser.getId());
			player.setNickname(currentUser.getNickname());
			gameboard.getPlayers().add(player);
		}

		gameboard.setAdmins(Sets.newHashSet(currentUser.getId()));

		return repository.save(gameboard);
	}

	@RequestMapping(value = "/api/board/{id}", method = RequestMethod.PUT)
	public Board updateGameboard(@PathVariable String id, @Valid @RequestBody Board gameboard)
			throws BadRequestException, ForbiddenException {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (!id.equals(gameboard.getId())) {
			throw new BadRequestException("IDs cannot be updated.");
		}

		Board board = repository.findOne(gameboard.getId());
		if (!board.getAdmins().contains(currentUser.getId())) {
			throw new ForbiddenException("Only board admins can perform this action");
		}

		// Make sure a owner is never removed from admins
		for (String owner : board.getOwners()) {
			gameboard.getAdmins().add(owner);
		}

		// Override owners. Do not allow modifications
		gameboard.setOwners(board.getOwners());

		return repository.save(gameboard);
	}

	@RequestMapping(value = "/api/board/{id}", method = RequestMethod.GET)
	public Board getGameboardById(@PathVariable String id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "/api/board", method = RequestMethod.GET, params = { "user" })
	public List<Board> getGameboardByUser(@RequestParam(value = "user", required = true) String userId) {
		return repository.findByPlayersUserId(userId);
	}


	@RequestMapping(value = "/api/board/{id}/reset", method = RequestMethod.POST)
	public Board resetBoard(@PathVariable String id)
			throws ForbiddenException {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Board board = repository.findOne(id);
		if (!board.getAdmins().contains(currentUser.getId())) {
			throw new ForbiddenException("You need to be a board admin to perform this action.");
		}

		for (PlayerStatistics player : board.getPlayers()) {
			player.setElo(1500);
			player.setMatchesPlayed(0);
			player.setMatchesPlayedAsResistance(0);
			player.setMatchesPlayedAsSpy(0);
			player.setMatchesWon(0);
			player.setMatchesWonAsResistance(0);
			player.setMatchesWonAsSpy(0);
		}

		board.setBoardStatistics(new BoardStatistics());

		// Intermediate save
		board = repository.save(board);

		List<ResistanceGame> games = gameRepository.findByBoardId(board.getId());
		Collections.sort(games, new ResistanceGameComparator());

		for (ResistanceGame game : games) {
			ratingServices.rateGame(game, board);
		}

		return board;

	}


}
