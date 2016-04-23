package com.marcolenzo.gameboard.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.marcolenzo.gameboard.annotations.ActionLoggable;
import com.marcolenzo.gameboard.exceptions.BadRequestException;
import com.marcolenzo.gameboard.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.model.Board;
import com.marcolenzo.gameboard.model.ResistanceGame;
import com.marcolenzo.gameboard.model.User;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(GameServices.class);

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

	public ResistanceGame castVote(String gameId, String playerId) throws BadRequestException, ForbiddenException {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ResistanceGame game = repository.findOne(gameId);
		if (game == null) {
			throw new BadRequestException("Unknown game.");
		}

		// Only one vote per player allowed.
		if (game.getVotes().keySet().contains(currentUser.getId())) {
			throw new ForbiddenException("You are allowed to vote once.");
		}

		// You cannot vote yourself
		if (currentUser.getId().equals(playerId)) {
			throw new ForbiddenException("Don't be a wanker. You cannot vote yourself.");
		}

		// Vote can be given only to winning team
		if (game.getResistanceWin() && game.getPlayers().contains(playerId) && !game.getSpies().contains(playerId)) {
			game.getVotes().put(currentUser.getId(), playerId);
			if (game.getVotesCount().get(playerId) == null) {
				game.getVotesCount().put(playerId, 1);
			} else {
				game.getVotesCount().put(playerId, game.getVotesCount().get(playerId) + 1);
			}
		}
		// could have been an OR with the first IF.
		else if (!game.getResistanceWin() && game.getSpies().contains(playerId)) {
			game.getVotes().put(currentUser.getId(), playerId);
			if (game.getVotesCount().get(playerId) == null) {
				game.getVotesCount().put(playerId, 1);
			} else {
				game.getVotesCount().put(playerId, game.getVotesCount().get(playerId) + 1);
			}
		} else {
			throw new BadRequestException("Who are you voting for?");
		}

		return repository.save(game);
	}

	@Scheduled(cron = "0 0/5 * * * ?")
	public void assignMvpTitle() {
		LOGGER.info("MVP Scheduled Job --- START ...");
		List<ResistanceGame> games = repository.findByMvpRatedIsFalse();

		for (ResistanceGame game : games) {
			LOGGER.info("MVP Scheduled Job --- Analyzing game {}", game.getId());
			if (game.getVoteUntil() != null && game.getVoteUntil().isBefore(LocalDateTime.now())) {
				String mvp = "";
				int v = 0;

				// If we have votes.
				if (game.getVotesCount() != null && !game.getVotesCount().isEmpty()) {
					for (String player : game.getVotesCount().keySet()) {
						int n = game.getVotesCount().get(player);
						if (n > v) {
							mvp = player;
							v = n;
						} else if (n == v) {
							LOGGER.info("MVP Scheduled Job --- MVP Tie!");
							// No clear MVP do not assign title
							mvp = "";
							v = 0;
							break;
						}
					}
				}
				LOGGER.info("MVP Scheduled Job --- Setting {} as MVP for game {}", mvp, game.getId());
				game.setMvp(mvp);
				game.setMvpRated(true);
				repository.save(game);
			} else {
				LOGGER.info("MVP Scheduled Job --- Election still open for game {}", game.getId());
			}
		}

		LOGGER.info("MVP Scheduled Job --- END.");
	}

}
