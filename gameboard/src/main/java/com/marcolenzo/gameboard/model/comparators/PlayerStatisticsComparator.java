package com.marcolenzo.gameboard.model.comparators;

import java.util.Comparator;

import com.marcolenzo.gameboard.model.PlayerStatistics;


public class PlayerStatisticsComparator implements Comparator<PlayerStatistics> {

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
