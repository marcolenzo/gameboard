package com.marcolenzo.gameboard.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcolenzo.gameboard.exceptions.BadRequestException;
import com.marcolenzo.gameboard.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.model.Board;
import com.marcolenzo.gameboard.services.BoardServices;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class BoardController {

	@Autowired
	private BoardServices boardServices;

	@RequestMapping(value = "/api/board", method = RequestMethod.POST)
	public Board createGameboard(@Valid @RequestBody Board board) {
		return boardServices.createBoard(board);
	}

	@RequestMapping(value = "/api/board/{id}", method = RequestMethod.PUT)
	public Board updateGameboard(@PathVariable String id, @Valid @RequestBody Board gameboard)
			throws BadRequestException, ForbiddenException {
		return boardServices.updateBoard(gameboard, id);
	}

	@RequestMapping(value = "/api/board/{id}", method = RequestMethod.GET)
	public Board getGameboardById(@PathVariable String id) {
		return boardServices.getBoardById(id);
	}

	@RequestMapping(value = "/api/board", method = RequestMethod.GET, params = { "user" })
	public List<Board> getGameboardByUser(@RequestParam(value = "user", required = true) String userId) {
		return boardServices.getBoardsByPlayerId(userId);
	}


	@RequestMapping(value = "/api/board/{id}/reset", method = RequestMethod.POST)
	public Board resetBoard(@PathVariable String id)
			throws ForbiddenException {
		return boardServices.resetBoard(id);
	}


}
