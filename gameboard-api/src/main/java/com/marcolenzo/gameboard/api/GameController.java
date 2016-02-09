package com.marcolenzo.gameboard.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private GameRepository repository;

	@RequestMapping(value = "/api/game", method = RequestMethod.GET, params = { "boardId" })
	public List<Game> getGamesByBoardId(@RequestParam(value = "boardId", required = true) String boardId) {
		return repository.findByBoardId(boardId);
	}


}
