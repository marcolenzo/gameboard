package com.marcolenzo.gameboard.commons.model;

import java.util.List;


public class Result {

	private String id;
	
	private String tableId;

	private String homeTeam;
	
	private List<String> homeTeamMembers;

	private String awayTeam;

	private String awayTeamMembers;
	
	private String winnerTeam;

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
	 * @return the tableId
	 */
	public String getTableId() {
		return tableId;
	}

	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	/**
	 * @return the homeTeam
	 */
	public String getHomeTeam() {
		return homeTeam;
	}

	/**
	 * @param homeTeam the homeTeam to set
	 */
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	/**
	 * @return the homeTeamMembers
	 */
	public List<String> getHomeTeamMembers() {
		return homeTeamMembers;
	}

	/**
	 * @param homeTeamMembers the homeTeamMembers to set
	 */
	public void setHomeTeamMembers(List<String> homeTeamMembers) {
		this.homeTeamMembers = homeTeamMembers;
	}

	/**
	 * @return the awayTeam
	 */
	public String getAwayTeam() {
		return awayTeam;
	}

	/**
	 * @param awayTeam the awayTeam to set
	 */
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	/**
	 * @return the awayTeamMembers
	 */
	public String getAwayTeamMembers() {
		return awayTeamMembers;
	}

	/**
	 * @param awayTeamMembers the awayTeamMembers to set
	 */
	public void setAwayTeamMembers(String awayTeamMembers) {
		this.awayTeamMembers = awayTeamMembers;
	}

	/**
	 * @return the winnerTeam
	 */
	public String getWinnerTeam() {
		return winnerTeam;
	}

	/**
	 * @param winnerTeam the winnerTeam to set
	 */
	public void setWinnerTeam(String winnerTeam) {
		this.winnerTeam = winnerTeam;
	}

}
