package com.marcolenzo.gameboard.model.comparators;

import java.util.Comparator;

import com.marcolenzo.gameboard.model.ResistanceGame;

/**
 * Comparator for comparing ResistanceGame objects based on their start time.
 * This comparator is used to sort ResistanceGame objects in ascending order of their start time.
 */
public class ResistanceGameComparator implements Comparator<ResistanceGame> {

    /**
     * Compares two ResistanceGame objects based on their start time.
     *
     * @param o1 the first ResistanceGame object
     * @param o2 the second ResistanceGame object
     * @return a negative integer, zero, or a positive integer as the first argument
     *         has a start time before, equal to, or after the second
     */
    @Override
    public int compare(ResistanceGame o1, ResistanceGame o2) {
        if (o1.getStartTime().isBefore(o2.getStartTime())) {
            return -1;
        } else if (o1.getStartTime().isAfter(o2.getStartTime())) {
            return 1;
        } else {
            return 0;
        }
    }
}
