package com.marcolenzo.gameboard.commons.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Player Statistics used to generate leaderboards.
 * @author Marco Lenzo
 *
 */
public class PlayerStatistics {

	@NotEmpty
	private String userId;

	@NotEmpty
	private String nickname;

	@NotNull
	private Integer elo = 1500;

	@NotNull
	private Integer matchesPlayed = 0;

	@NotNull
	private Integer matchesWon = 0;

	@NotNull
	private Integer matchesPlayedAsResistance = 0;

	@NotNull
	private Integer matchesWonAsResistance = 0;

	@NotNull
	private Integer matchesPlayedAsSpy = 0;

	@NotNull
	private Integer matchesWonAsSpy = 0;

	@NotNull
	private Integer eloVariation = 0;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the elo
	 */
	public Integer getElo() {
		return elo;
	}

	/**
	 * @param elo the elo to set
	 */
	public void setElo(Integer elo) {
		this.elo = elo;
	}

	/**
	 * @return the matchesPlayed
	 */
	public Integer getMatchesPlayed() {
		return matchesPlayed;
	}

	/**
	 * @param matchesPlayed the matchesPlayed to set
	 */
	public void setMatchesPlayed(Integer matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	/**
	 * @return the matchesWon
	 */
	public Integer getMatchesWon() {
		return matchesWon;
	}

	/**
	 * @param matchesWon the matchesWon to set
	 */
	public void setMatchesWon(Integer matchesWon) {
		this.matchesWon = matchesWon;
	}

	/**
	 * @return the matchesPlayedAsResistance
	 */
	public Integer getMatchesPlayedAsResistance() {
		return matchesPlayedAsResistance;
	}

	/**
	 * @param matchesPlayedAsResistance the matchesPlayedAsResistance to set
	 */
	public void setMatchesPlayedAsResistance(Integer matchesPlayedAsResistance) {
		this.matchesPlayedAsResistance = matchesPlayedAsResistance;
	}

	/**
	 * @return the matchesWonAsResistance
	 */
	public Integer getMatchesWonAsResistance() {
		return matchesWonAsResistance;
	}

	/**
	 * @param matchesWonAsResistance the matchesWonAsResistance to set
	 */
	public void setMatchesWonAsResistance(Integer matchesWonAsResistance) {
		this.matchesWonAsResistance = matchesWonAsResistance;
	}

	/**
	 * @return the matchesPlayedAsSpy
	 */
	public Integer getMatchesPlayedAsSpy() {
		return matchesPlayedAsSpy;
	}

	/**
	 * @param matchesPlayedAsSpy the matchesPlayedAsSpy to set
	 */
	public void setMatchesPlayedAsSpy(Integer matchesPlayedAsSpy) {
		this.matchesPlayedAsSpy = matchesPlayedAsSpy;
	}

	/**
	 * @return the matchesWonAsSpy
	 */
	public Integer getMatchesWonAsSpy() {
		return matchesWonAsSpy;
	}

	/**
	 * @param matchesWonAsSpy the matchesWonAsSpy to set
	 */
	public void setMatchesWonAsSpy(Integer matchesWonAsSpy) {
		this.matchesWonAsSpy = matchesWonAsSpy;
	}

	/**
	 * @return the eloVariation
	 */
	public Integer getEloVariation() {
		return eloVariation;
	}

	/**
	 * @param eloVariation the eloVariation to set
	 */
	public void setEloVariation(Integer eloVariation) {
		this.eloVariation = eloVariation;
	}

}
