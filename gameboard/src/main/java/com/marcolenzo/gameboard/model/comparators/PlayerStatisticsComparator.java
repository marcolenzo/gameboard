package com.marcolenzo.gameboard.model.comparators;

import java.util.Comparator;

import com.marcolenzo.gameboard.model.PlayerStatistics;

/**
 * Comparator for comparing PlayerStatistics based on their ELO rating.
 * This comparator is used to sort players in descending order of their ELO rating.
 */
public class PlayerStatisticsComparator implements Comparator<PlayerStatistics> {

	/**
	 * Compares two PlayerStatistics objects based on their ELO rating.
	 *
	 * @param arg0 the first PlayerStatistics object
	 * @param arg1 the second PlayerStatistics object
	 * @return a negative integer, zero, or a positive integer as the first argument
	 *         has a greater than, equal to, or less than ELO rating compared to the second
	 */
	@Override
	public int compare(PlayerStatistics arg0, PlayerStatistics arg1) {
		if (arg0.getElo() < arg1.getElo()) {
			return 1;
		}
		else if (arg0.getElo() > arg1.getElo()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
