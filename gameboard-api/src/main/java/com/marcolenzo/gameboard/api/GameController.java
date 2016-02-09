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

import com.marcolenzo.gameboard.commons.model.Game;
import com.marcolenzo.gameboard.commons.repositories.GameRepository;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameboardController.class);

	@Autowired
	private GameRepository repository;

	@RequestMapping(value = "/api/game", method = RequestMethod.GET, params = { "boardId" })
	public List<Game> getGamesByBoardId(@RequestParam(value = "boardId", required = true) String boardId) {
		return repository.findByBoardId(boardId);
	}

	@RequestMapping(value = "/api/game", method = RequestMethod.POST)
	public Game createGame(@Valid @RequestBody Game game) {
		LOGGER.info("Creating new game for board {}", game.getBoardId());
		return repository.save(game);
	}

	@RequestMapping(value = "/api/game/test", method = RequestMethod.GET)
	public Game getGameTest() {
		Game game = new Game();
		game.setId(UUID.randomUUID().toString());
		game.setBoardId(UUID.randomUUID().toString());
		game.setStartTime(LocalDateTime.now());
		return game;
	}


}
