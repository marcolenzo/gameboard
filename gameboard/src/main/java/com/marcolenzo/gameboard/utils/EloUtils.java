package com.marcolenzo.gameboard.utils;

import com.marcolenzo.gameboard.model.BoardStatistics;

public class EloUtils {

	private EloUtils() {

	}

	public static int computeElo(int currentElo, int score, double opponentsElo) {
		double tranformedElo = (int) Math.pow(10, (double) currentElo / 400);
		double expectedScore = tranformedElo / (tranformedElo + opponentsElo);
		return (int) (currentElo + (32 * (score - expectedScore)));
	}

	public static int computeFairElo(int currentElo, int score, double opponentsElo, BoardStatistics boardStats, int numberOfPlayers, boolean resistance) {
		double tranformedElo = (int) Math.pow(10, (double) currentElo / 400);
		double expectedScore = tranformedElo / (tranformedElo + opponentsElo);
		double fairExpectedScore = (expectedScore + getExpectedScoreByNumberOfPlayers(boardStats, numberOfPlayers,
				resistance)) / 2;
		return (int) (currentElo + (32 * (score - fairExpectedScore)));
	}


	public static double getExpectedScoreByNumberOfPlayers(BoardStatistics boardStats, int numberOfPlayers,
			boolean resistance) {

		switch (numberOfPlayers) {
		case 5:
			if (boardStats.getMatchesPlayed3v2() == 0) {
				return 0.5;
			}
			return computeWinRatio(boardStats.getMatchesPlayed3v2(), boardStats.getMatchesWonByResistance3v2(),
					resistance);
		case 6:
			if (boardStats.getMatchesPlayed4v2() == 0) {
				return 0.5;
			}
			return computeWinRatio(boardStats.getMatchesPlayed4v2(), boardStats.getMatchesWonByResistance4v2(),
					resistance);
		case 7:
			if (boardStats.getMatchesPlayed4v3() == 0) {
				return 0.5;
			}
			return computeWinRatio(boardStats.getMatchesPlayed4v3(), boardStats.getMatchesWonByResistance4v3(),
					resistance);
		case 8:
			if (boardStats.getMatchesPlayed5v3() == 0) {
				return 0.5;
			}
			return computeWinRatio(boardStats.getMatchesPlayed5v3(), boardStats.getMatchesWonByResistance5v3(),
					resistance);
		case 9:
			if (boardStats.getMatchesPlayed6v3() == 0) {
				return 0.5;
			}
			return computeWinRatio(boardStats.getMatchesPlayed6v3(), boardStats.getMatchesWonByResistance6v3(),
					resistance);
		case 10:
			if (boardStats.getMatchesPlayed6v4() == 0) {
				return 0.5;
			}
			return computeWinRatio(boardStats.getMatchesPlayed6v4(), boardStats.getMatchesWonByResistance6v4(),
					resistance);
		}

		return 0.5;
	}

	private static double computeWinRatio(int matchesPlayed, int matchesWonByResistance, boolean resistance) {
		if (resistance) {
			return (double) matchesWonByResistance / matchesPlayed;
		}
		else {
			return ((double) matchesPlayed - matchesWonByResistance) / matchesPlayed;
		}
	}

}
