/**
 * 
 */
package com.marcolenzo.gameboard.api.services;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.commons.model.Board;
import com.marcolenzo.gameboard.commons.model.PlayerStatistics;
import com.marcolenzo.gameboard.commons.model.ResistanceGame;
import com.marcolenzo.gameboard.commons.repositories.BoardRepository;
import com.marcolenzo.gameboard.commons.repositories.ResistanceGameRepository;


/**
 * Services used to rate players.
 * @author Marco Lenzo
 */
@Component
public class RatingServices {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ResistanceGameRepository repository;


	public ResistanceGame rateGame(ResistanceGame game, Board board) {

		resetEloVariations(board);

		Set<PlayerStatistics> resistance = Sets.newHashSet();
		Set<PlayerStatistics> spies = Sets.newHashSet();
		for (String userId : game.getPlayers()) {
			if (game.getSpies().contains(userId)) {
				spies.add(board.getPlayersMap().get(userId));
			}
			else {
				resistance.add(board.getPlayersMap().get(userId));
			}
		}

		// Compute average resistance ELO
		double resistanceElo = 0;
		int count = 0;
		for (PlayerStatistics user : resistance) {
			resistanceElo += user.getElo();
			count++;
		}
		resistanceElo = resistanceElo / count;

		// Compute average spies ELO
		double spiesElo = 0;
		count = 0;
		for (PlayerStatistics user : spies) {
			spiesElo += user.getElo();
			count++;
		}
		spiesElo = spiesElo / count;

		game.setResistanceElo((int) resistanceElo);
		game.setSpiesElo((int) spiesElo);

		// Transform elos
		resistanceElo = (int) Math.pow(10, resistanceElo / 400);
		spiesElo = (int) Math.pow(10, spiesElo / 400);

		for (PlayerStatistics user : resistance) {
			ratePlayer(user, game.getBoardId(), game.getResistanceWin() ? 1 : 0, spiesElo, false);
		}

		for (PlayerStatistics user : spies) {
			ratePlayer(user, game.getBoardId(), game.getResistanceWin() ? 0 : 1, resistanceElo, true);
		}

		board = boardRepository.save(board);

		// Save player statics in game as well to have leadboard history
		game.setPlayerStats(board.getPlayers());

		return repository.save(game);
	}


	/**
	 * 
	 * @param user
	 * @param boardId
	 * @param score
	 * @param opponentsElo
	 * @param isSpy
	 */
	private void ratePlayer(PlayerStatistics user, String boardId, int score, double opponentsElo,
			boolean isSpy) {
		Integer elo = user.getElo();
		// Transform user's elo
		double tranformedElo = (int) Math.pow(10, (double) elo / 400);
		double expectedScore = tranformedElo / (tranformedElo + opponentsElo);
		Integer finalElo = (int) (elo + (32 * (score - expectedScore)));

		user.setElo(finalElo);
		user.setMatchesPlayed(user.getMatchesPlayed() + 1);
		if (isSpy) {
			user.setMatchesPlayedAsSpy(user.getMatchesPlayedAsSpy() + 1);
			if (score == 1) {
				user.setMatchesWonAsSpy(user.getMatchesWonAsSpy() + 1);
				user.setMatchesWon(user.getMatchesWon() + 1);
			}
		}
		else {
			user.setMatchesPlayedAsResistance(user.getMatchesPlayedAsResistance() + 1);
			if (score == 1) {
				user.setMatchesWonAsResistance(user.getMatchesWonAsResistance() + 1);
				user.setMatchesWon(user.getMatchesWon() + 1);
			}
		}

		user.setEloVariation(finalElo - elo);
	}

	private void resetEloVariations(Board board) {
		for (PlayerStatistics stat : board.getPlayers()) {
			stat.setEloVariation(0);
		}
	}


}
