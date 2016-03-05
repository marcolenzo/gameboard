/**
 * 
 */
package com.marcolenzo.gameboard.model.comparators;


import java.util.Comparator;

import com.marcolenzo.gameboard.model.ResistanceGame;




/**
 * @author Marco Lenzo
 *
 */
public class ResistanceGameComparator
		implements Comparator<ResistanceGame> {

	@Override
	public int compare(ResistanceGame o1, ResistanceGame o2) {
		if (o1.getStartTime().isBefore(o2.getStartTime())) {
			return -1;
		}
		else if (o1.getStartTime().isAfter(o2.getStartTime())) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
