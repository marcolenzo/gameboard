package com.marcolenzo.gameboard.model;

import javax.validation.constraints.NotNull;

/**
 * Represents the statistics of a game board.
 * This class contains various statistics related to matches played on the board.
 * It includes the number of matches played, matches won by resistance,
 * and matches played and won in different configurations.
 */
public class BoardStatistics {

	@NotNull
	private Integer matchesPlayed = 0;

	@NotNull
	private Integer matchesWonByResistance = 0;

	@NotNull
	private Integer matchesPlayed3v2 = 0;

	@NotNull
	private Integer matchesWonByResistance3v2 = 0;

	@NotNull
	private Integer matchesPlayed4v2 = 0;

	@NotNull
	private Integer matchesWonByResistance4v2 = 0;

	@NotNull
	private Integer matchesPlayed4v3 = 0;

	@NotNull
	private Integer matchesWonByResistance4v3 = 0;

	@NotNull
	private Integer matchesPlayed5v3 = 0;

	@NotNull
	private Integer matchesWonByResistance5v3 = 0;

	@NotNull
	private Integer matchesPlayed6v3 = 0;

	@NotNull
	private Integer matchesWonByResistance6v3 = 0;

	@NotNull
	private Integer matchesPlayed6v4 = 0;

	@NotNull
	private Integer matchesWonByResistance6v4 = 0;

	/**
	 * Gets the number of matches played on the board.
	 *
	 * @return the number of matches played
	 */
	public Integer getMatchesPlayed() {
		return matchesPlayed;
	}

	/**
	 * Sets the number of matches played on the board.
	 *
	 * @param matchesPlayed the number of matches played to set
	 */
	public void setMatchesPlayed(Integer matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	/**
	 * Gets the number of matches won by the resistance.
	 *
	 * @return the number of matches won by the resistance
	 */
	public Integer getMatchesWonByResistance() {
		return matchesWonByResistance;
	}

	/**
	 * Sets the number of matches won by the resistance.
	 *
	 * @param matchesWonByResistance the number of matches won by the resistance to set
	 */
	public void setMatchesWonByResistance(Integer matchesWonByResistance) {
		this.matchesWonByResistance = matchesWonByResistance;
	}

	/**
	 * Gets the number of matches played in a 3v2 configuration.
	 *
	 * @return the number of matches played in a 3v2 configuration
	 */
	public Integer getMatchesPlayed3v2() {
		return matchesPlayed3v2;
	}

	/**
	 * Sets the number of matches played in a 3v2 configuration.
	 *
	 * @param matchesPlayed3v2 the number of matches played in a 3v2 configuration to set
	 */
	public void setMatchesPlayed3v2(Integer matchesPlayed3v2) {
		this.matchesPlayed3v2 = matchesPlayed3v2;
	}

	/**
	 * Gets the number of matches won by the resistance in a 3v2 configuration.
	 *
	 * @return the number of matches won by the resistance in a 3v2 configuration
	 */
	public Integer getMatchesWonByResistance3v2() {
		return matchesWonByResistance3v2;
	}

	/**
	 * Sets the number of matches won by the resistance in a 3v2 configuration.
	 *
	 * @param matchesWonByResistance3v2 the number of matches won by the resistance in a 3v2 configuration to set
	 */
	public void setMatchesWonByResistance3v2(Integer matchesWonByResistance3v2) {
		this.matchesWonByResistance3v2 = matchesWonByResistance3v2;
	}

	/**
	 * Gets the number of matches played in a 4v2 configuration.
	 *
	 * @return the number of matches played in a 4v2 configuration
	 */
	public Integer getMatchesPlayed4v2() {
		return matchesPlayed4v2;
	}

	/**
	 * Sets the number of matches played in a 4v2 configuration.
	 *
	 * @param matchesPlayed4v2 the number of matches played in a 4v2 configuration to set
	 */
	public void setMatchesPlayed4v2(Integer matchesPlayed4v2) {
		this.matchesPlayed4v2 = matchesPlayed4v2;
	}

	/**
	 * Gets the number of matches won by the resistance in a 4v2 configuration.
	 *
	 * @return the number of matches won by the resistance in a 4v2 configuration
	 */
	public Integer getMatchesWonByResistance4v2() {
		return matchesWonByResistance4v2;
	}

	/**
	 * Sets the number of matches won by the resistance in a 4v2 configuration.
	 *
	 * @param matchesWonByResistance4v2 the number of matches won by the resistance in a 4v2 configuration to set
	 */
	public void setMatchesWonByResistance4v2(Integer matchesWonByResistance4v2) {
		this.matchesWonByResistance4v2 = matchesWonByResistance4v2;
	}

	/**
	 * Gets the number of matches played in a 4v3 configuration.
	 *
	 * @return the number of matches played in a 4v3 configuration
	 */
	public Integer getMatchesPlayed4v3() {
		return matchesPlayed4v3;
	}

	/**
	 * Sets the number of matches played in a 4v3 configuration.
	 *
	 * @param matchesPlayed4v3 the number of matches played in a 4v3 configuration to set
	 */
	public void setMatchesPlayed4v3(Integer matchesPlayed4v3) {
		this.matchesPlayed4v3 = matchesPlayed4v3;
	}

	/**
	 * Gets the number of matches won by the resistance in a 4v3 configuration.
	 *
	 * @return the number of matches won by the resistance in a 4v3 configuration
	 */
	public Integer getMatchesWonByResistance4v3() {
		return matchesWonByResistance4v3;
	}

	/**
	 * Sets the number of matches won by the resistance in a 4v3 configuration.
	 *
	 * @param matchesWonByResistance4v3 the number of matches won by the resistance in a 4v3 configuration to set
	 */
	public void setMatchesWonByResistance4v3(Integer matchesWonByResistance4v3) {
		this.matchesWonByResistance4v3 = matchesWonByResistance4v3;
	}

	/**
	 * Gets the number of matches played in a 5v3 configuration.
	 *
	 * @return the number of matches played in a 5v3 configuration
	 */
	public Integer getMatchesPlayed5v3() {
		return matchesPlayed5v3;
	}

	/**
	 * Sets the number of matches played in a 5v3 configuration.
	 *
	 * @param matchesPlayed5v3 the number of matches played in a 5v3 configuration to set
	 */
	public void setMatchesPlayed5v3(Integer matchesPlayed5v3) {
		this.matchesPlayed5v3 = matchesPlayed5v3;
	}

	/**
	 * Gets the number of matches won by the resistance in a 5v3 configuration.
	 *
	 * @return the number of matches won by the resistance in a 5v3 configuration
	 */
	public Integer getMatchesWonByResistance5v3() {
		return matchesWonByResistance5v3;
	}

	/**
	 * Sets the number of matches won by the resistance in a 5v3 configuration.
	 *
	 * @param matchesWonByResistance5v3 the number of matches won by the resistance in a 5v3 configuration to set
	 */
	public void setMatchesWonByResistance5v3(Integer matchesWonByResistance5v3) {
		this.matchesWonByResistance5v3 = matchesWonByResistance5v3;
	}

	/**
	 * Gets the number of matches played in a 6v4 configuration.
	 *
	 * @return the number of matches played in a 6v4 configuration
	 */
	public Integer getMatchesPlayed6v4() {
		return matchesPlayed6v4;
	}

	/**
	 * Sets the number of matches played in a 6v4 configuration.
	 *
	 * @param matchesPlayed6v4 the number of matches played in a 6v4 configuration to set
	 */
	public void setMatchesPlayed6v4(Integer matchesPlayed6v4) {
		this.matchesPlayed6v4 = matchesPlayed6v4;
	}

	/**
	 * Gets the number of matches won by the resistance in a 6v4 configuration.
	 *
	 * @return the number of matches won by the resistance in a 6v4 configuration
	 */
	public Integer getMatchesWonByResistance6v4() {
		return matchesWonByResistance6v4;
	}

	/**
	 * Sets the number of matches won by the resistance in a 6v4 configuration.
	 *
	 * @param matchesWonByResistance6v4 the number of matches won by the resistance in a 6v4 configuration to set
	 */
	public void setMatchesWonByResistance6v4(Integer matchesWonByResistance6v4) {
		this.matchesWonByResistance6v4 = matchesWonByResistance6v4;
	}

	/**
	 * Gets the number of matches played in a 6v3 configuration.
	 *
	 * @return the number of matches played in a 6v3 configuration
	 */
	public Integer getMatchesPlayed6v3() {
		return matchesPlayed6v3;
	}

	/**
	 * Sets the number of matches played in a 6v3 configuration.
	 *
	 * @param matchesPlayed6v3 the number of matches played in a 6v3 configuration to set
	 */
	public void setMatchesPlayed6v3(Integer matchesPlayed6v3) {
		this.matchesPlayed6v3 = matchesPlayed6v3;
	}

	/**
	 * Gets the number of matches won by the resistance in a 6v3 configuration.
	 *
	 * @return the number of matches won by the resistance in a 6v3 configuration
	 */
	public Integer getMatchesWonByResistance6v3() {
		return matchesWonByResistance6v3;
	}

	/**
	 * Sets the number of matches won by the resistance in a 6v3 configuration.
	 *
	 * @param matchesWonByResistance6v3 the number of matches won by the resistance in a 6v3 configuration to set
	 */
	public void setMatchesWonByResistance6v3(Integer matchesWonByResistance6v3) {
		this.matchesWonByResistance6v3 = matchesWonByResistance6v3;
	}

}
