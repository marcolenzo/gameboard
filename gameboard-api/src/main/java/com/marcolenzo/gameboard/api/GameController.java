package com.marcolenzo.gameboard.api;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.api.exceptions.BadRequestException;
import com.marcolenzo.gameboard.api.services.RatingServices;
import com.marcolenzo.gameboard.commons.comparators.ResistanceGameComparator;
import com.marcolenzo.gameboard.commons.model.Board;
import com.marcolenzo.gameboard.commons.model.ResistanceGame;
import com.marcolenzo.gameboard.commons.repositories.BoardRepository;
import com.marcolenzo.gameboard.commons.repositories.ResistanceGameRepository;
import com.marcolenzo.gameboard.commons.repositories.UserRepository;
import com.marcolenzo.gameboard.commons.validator.ResistanceGameValidator;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private ResistanceGameRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private RatingServices ratingServices;

	@Autowired
	private ResistanceGameValidator gameValidator;

	@RequestMapping(value = "/api/game", method = RequestMethod.GET, params = { "boardId" })
	public List<ResistanceGame> getGamesByBoardId(@RequestParam(value = "boardId", required = true) String boardId) {
		List<ResistanceGame> games = repository.findByBoardId(boardId);
		Collections.sort(games, Collections.reverseOrder(new ResistanceGameComparator()));
		return games;
	}

	@RequestMapping(value = "/api/game/{id}", method = RequestMethod.GET)
	public ResistanceGame getGame(@PathVariable String id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "/api/game", method = RequestMethod.POST)
	public ResistanceGame createGame(@Valid @RequestBody ResistanceGame game, BindingResult result)
			throws BadRequestException {
		LOGGER.info("Creating new game for board {}", game.getBoardId());
		gameValidator.validate(game, result);
		if (result.hasErrors()) {
			throw new BadRequestException(result.getGlobalError().getCode());
		}
		Board board = boardRepository.findOne(game.getBoardId());
		return ratingServices.rateGame(game, board);
	}

	@RequestMapping(value = "/api/game/test", method = RequestMethod.GET)
	public ResistanceGame getGameTest() {
		ResistanceGame game = new ResistanceGame();
		game.setId(UUID.randomUUID().toString());
		game.setBoardId(UUID.randomUUID().toString());
		game.setStartTime(LocalDateTime.now());
		game.setPlayers(Sets.newHashSet("123", "abc", "xxx", "yyy", "zzz"));
		game.setSpies(Sets.newHashSet("123", "abc"));
		game.setResistanceWin(true);
		return game;
	}



}
