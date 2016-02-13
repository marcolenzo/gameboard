package com.marcolenzo.gameboard.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.commons.model.ResistanceGame;
import com.marcolenzo.gameboard.commons.repositories.ResistanceGameRepository;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class ResistanceGameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private ResistanceGameRepository repository;

	@RequestMapping(value = "/api/resistance_game", method = RequestMethod.GET, params = { "boardId" })
	public List<ResistanceGame> getGamesByBoardId(@RequestParam(value = "boardId", required = true) String boardId) {
		return repository.findByBoardId(boardId);
	}

	@RequestMapping(value = "/api/resistance_game", method = RequestMethod.POST)
	public ResistanceGame createGame(@Valid @RequestBody ResistanceGame game) {
		LOGGER.info("Creating new game for board {}", game.getBoardId());
		return repository.save(game);
	}

	@RequestMapping(value = "/api/resistance_game/test", method = RequestMethod.GET)
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
