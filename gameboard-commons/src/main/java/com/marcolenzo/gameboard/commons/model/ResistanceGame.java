package com.marcolenzo.gameboard.commons.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Resistance Game data.
 * @author Marco Lenzo
 *
 */
@Document
public class ResistanceGame {

	@Id
	private String id;

	@Indexed
	private String boardId;

	private LocalDateTime startTime;

	@NotNull
	private Boolean resistanceWin;

	@NotEmpty
	@NotNull
	private Set<String> players;
	
	@NotEmpty
	@NotNull
	private Set<String> spies;

	private Integer resistanceElo;

	private Integer spiesElo;

	private Boolean isRated = false;

	private Map<String, Integer> eloVariations;

	private Set<PlayerStatistics> playerStats;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the boardId
	 */
	public String getBoardId() {
		return boardId;
	}

	/**
	 * @param boardId the boardId to set
	 */
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	/**
	 * @return the startTime
	 */
	public LocalDateTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the resistanceWin
	 */
	public Boolean getResistanceWin() {
		return resistanceWin;
	}

	/**
	 * @param resistanceWin the resistanceWin to set
	 */
	public void setResistanceWin(Boolean resistanceWin) {
		this.resistanceWin = resistanceWin;
	}

	/**
	 * @return the players
	 */
	public Set<String> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(Set<String> players) {
		this.players = players;
	}

	/**
	 * @return the spies
	 */
	public Set<String> getSpies() {
		return spies;
	}

	/**
	 * @param spies the spies to set
	 */
	public void setSpies(Set<String> spies) {
		this.spies = spies;
	}

	/**
	 * @return the isRated
	 */
	public Boolean getIsRated() {
		return isRated;
	}

	/**
	 * @param isRated the isRated to set
	 */
	public void setIsRated(Boolean isRated) {
		this.isRated = isRated;
	}

	/**
	 * @return the resistanceElo
	 */
	public Integer getResistanceElo() {
		return resistanceElo;
	}

	/**
	 * @param resistanceElo the resistanceElo to set
	 */
	public void setResistanceElo(Integer resistanceElo) {
		this.resistanceElo = resistanceElo;
	}

	/**
	 * @return the spiesElo
	 */
	public Integer getSpiesElo() {
		return spiesElo;
	}

	/**
	 * @param spiesElo the spiesElo to set
	 */
	public void setSpiesElo(Integer spiesElo) {
		this.spiesElo = spiesElo;
	}

	/**
	 * @return the playerStats
	 */
	public Set<PlayerStatistics> getPlayerStats() {
		return playerStats;
	}

	/**
	 * @param playerStats the playerStats to set
	 */
	public void setPlayerStats(Set<PlayerStatistics> playerStats) {
		this.playerStats = playerStats;
	}

	/**
	 * @return the eloVariations
	 */
	public Map<String, Integer> getEloVariations() {
		return eloVariations;
	}

	/**
	 * @param eloVariations the eloVariations to set
	 */
	public void setEloVariations(Map<String, Integer> eloVariations) {
		this.eloVariations = eloVariations;
	}

}
