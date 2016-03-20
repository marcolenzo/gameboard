package com.marcolenzo.gameboard.controllers;

import java.util.List;

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

import com.marcolenzo.gameboard.exceptions.BadRequestException;
import com.marcolenzo.gameboard.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.model.ResistanceGame;
import com.marcolenzo.gameboard.model.validators.ResistanceGameValidator;
import com.marcolenzo.gameboard.services.GameServices;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	@Autowired
	private GameServices gameServices;

	@Autowired
	private ResistanceGameValidator gameValidator;

	@RequestMapping(value = "/api/game", method = RequestMethod.GET, params = { "boardId" })
	public List<ResistanceGame> getGamesByBoardId(@RequestParam(value = "boardId", required = true) String boardId) {
		return gameServices.getGamesByBoardId(boardId);
	}

	@RequestMapping(value = "/api/game/{id}", method = RequestMethod.GET)
	public ResistanceGame getGame(@PathVariable String id) {
		return gameServices.getGameById(id);
	}
	
	@RequestMapping(value = "/api/game/{id}", method = RequestMethod.DELETE)
	public void deleteGame(@PathVariable String id) throws ForbiddenException {
		gameServices.deleteGame(id);
	}

	@RequestMapping(value = "/api/game", method = RequestMethod.POST)
	public ResistanceGame createGame(@Valid @RequestBody ResistanceGame game, BindingResult result)
			throws BadRequestException {
		LOGGER.info("Creating new game for board {}", game.getBoardId());
		gameValidator.validate(game, result);
		if (result.hasErrors()) {
			throw new BadRequestException(result.getGlobalError().getCode());
		}
		return gameServices.createAndRateGame(game);
	}

	@RequestMapping(value = "/api/game/{id}/vote", method = RequestMethod.POST)
	public ResistanceGame castVote(@PathVariable String id, @RequestBody String playerId) throws BadRequestException,
			ForbiddenException {
		return gameServices.castVote(id, playerId);
	}

}
