/**
 * 
 */
package com.marcolenzo.gameboard.api.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.commons.comparators.PlayerStatisticsComparator;
import com.marcolenzo.gameboard.commons.model.Board;
import com.marcolenzo.gameboard.commons.model.BoardStatistics;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(RatingServices.class);

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

		Collections.sort(board.getPlayers(), new PlayerStatisticsComparator());

		// Set positions game
		setPositions(board.getPlayers());
		board.setBoardStatistics(updateBoardStatistics(board, game));

		board = boardRepository.save(board);

		// Save player statics in game as well to have leaderboard history
		List<PlayerStatistics> gamePlayerStatistics = Lists.newArrayList();
		for (PlayerStatistics playerStatistics : board.getPlayers()) {
			if (playerStatistics.getMatchesPlayed() > 0) {
				gamePlayerStatistics.add(playerStatistics);
			}
		}
		game.setPlayerStats(gamePlayerStatistics);

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
	private void ratePlayer(PlayerStatistics user, String boardId, int score, double opponentsElo, boolean isSpy) {
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

	private void setPositions(List<PlayerStatistics> playerStat) {
		// Set positions and store in game
		int rating = 0;
		int rank = 1;
		for (PlayerStatistics player : playerStat) {
			int initialPos = player.getPosition();
			if (player.getElo() == rating) {
				player.setPosition(rank - 1);
			}
			else {
				player.setPosition(rank);
				rank++;
				rating = player.getElo();
			}
			player.setPositionVariation(initialPos - player.getPosition());
		}
	}

	private BoardStatistics updateBoardStatistics(Board board, ResistanceGame game) {
		BoardStatistics stats = board.getBoardStatistics();
		if (stats == null) {
			stats = new BoardStatistics();
		}
		stats.setMatchesPlayed(stats.getMatchesPlayed() + 1);
		if (game.getResistanceWin()) {
			stats.setMatchesWonByResistance(stats.getMatchesWonByResistance() + 1);
		}
		switch (game.getPlayers().size()) {
		case 5:
			stats.setMatchesPlayed3v2(stats.getMatchesPlayed3v2() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance3v2(stats.getMatchesWonByResistance3v2() + 1);
			}
			break;
		case 6:
			stats.setMatchesPlayed4v2(stats.getMatchesPlayed4v2() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance4v2(stats.getMatchesWonByResistance4v2() + 1);
			}
			break;
		case 7:
			stats.setMatchesPlayed4v3(stats.getMatchesPlayed4v3() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance4v3(stats.getMatchesWonByResistance4v3() + 1);
			}
			break;
		case 8:
			stats.setMatchesPlayed5v3(stats.getMatchesPlayed5v3() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance5v3(stats.getMatchesWonByResistance5v3() + 1);
			}
			break;
		case 9:
			stats.setMatchesPlayed6v3(stats.getMatchesPlayed6v3() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance6v3(stats.getMatchesWonByResistance6v3() + 1);
			}
			break;
		case 10:
			stats.setMatchesPlayed6v4(stats.getMatchesPlayed6v4() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance6v4(stats.getMatchesWonByResistance6v4() + 1);
			}
			break;
		default:
			LOGGER.warn("Unexpected number of players");
		}
		return stats;
	}

}
