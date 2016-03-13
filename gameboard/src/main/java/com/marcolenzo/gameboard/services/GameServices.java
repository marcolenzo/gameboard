package com.marcolenzo.gameboard.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcolenzo.gameboard.annotations.ActionLoggable;
import com.marcolenzo.gameboard.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.model.Board;
import com.marcolenzo.gameboard.model.ResistanceGame;
import com.marcolenzo.gameboard.model.comparators.ResistanceGameComparator;
import com.marcolenzo.gameboard.model.validators.ResistanceGameValidator;
import com.marcolenzo.gameboard.repositories.ResistanceGameRepository;

/**
 * Services for the management of games.
 * @author Marco Lenzo
 *
 */
@Component
public class GameServices {

	@Autowired
	private ResistanceGameRepository repository;

	@Autowired
	private ResistanceGameValidator gameValidator;

	@Autowired
	private BoardServices boardServices;

	public ResistanceGame getGameById(String id) {
		return repository.findOne(id);
	}
	
	@ActionLoggable
	public ResistanceGame deleteGame(String id) throws ForbiddenException {
		ResistanceGame game = repository.findOne(id);
		boardServices.isCurrentUserAdminCheck(game.getBoardId());
		repository.delete(id);
		boardServices.resetBoard(game.getBoardId());
		return game;
	}

	public List<ResistanceGame> getGamesByBoardId(String boardId) {
		List<ResistanceGame> games = repository.findByBoardId(boardId);
		Collections.sort(games, Collections.reverseOrder(new ResistanceGameComparator()));
		return games;
	}

	public ResistanceGame createAndRateGame(ResistanceGame game) {
		Board board = boardServices.getBoardById(game.getBoardId());
		return boardServices.rateGame(game, board);
	}

}
