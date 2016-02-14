package com.marcolenzo.gameboard.api;

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
import com.marcolenzo.gameboard.commons.model.Board;
import com.marcolenzo.gameboard.commons.model.BoardPlayer;
import com.marcolenzo.gameboard.commons.model.User;
import com.marcolenzo.gameboard.commons.repositories.BoardRepository;
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

	@RequestMapping(value = "/api/board", method = RequestMethod.POST)
	public Board createGameboard(@Valid @RequestBody Board gameboard) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Do not accept externally defined IDs.
		gameboard.setId(null);

		// Sanitize player set and make sure current user is present
		boolean isCurrentUserPresent = false;
		for (BoardPlayer player : gameboard.getPlayers()) {
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
		}

		if (!isCurrentUserPresent) {
			BoardPlayer player = new BoardPlayer();
			player.setUserId(currentUser.getId());
			player.setNickname(currentUser.getNickname());
			gameboard.getPlayers().add(player);
		}

		gameboard.setAdmins(Sets.newHashSet(currentUser.getId()));

		return repository.save(gameboard);
	}

	@RequestMapping(value = "/api/board/{id}", method = RequestMethod.PUT)
	public Board updateGameboard(@PathVariable String id, @Valid @RequestBody Board gameboard)
			throws BadRequestException {

		if (!id.equals(gameboard.getId())) {
			throw new BadRequestException("IDs cannot be updated.");
		}

		// TODO sanitize records for newly added users.

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

}
