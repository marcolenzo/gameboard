package com.marcolenzo.gameboard.commons.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A planned game session where multiple tables and multiple games can be setup.
 * @author Marco Lenzo
 *
 */
public class GameSession {

	private String id;

	private String boardId;

	private List<String> tables;

	private List<String> results;

	private Status status;
	
	private LocalDateTime startTime; 

	private List<String> users;
	

	public enum Status {
		ACCEPTING_REGISTRATION, REGISTRATION_CLOSED, AWAITING_START, IN_PROGRESS, AWAITING_RESULT, DONE;
	}

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
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
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
	 * @return the users
	 */
	public List<String> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<String> users) {
		this.users = users;
	}

	/**
	 * @return the tables
	 */
	public List<String> getTables() {
		return tables;
	}

	/**
	 * @param tables the tables to set
	 */
	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	/**
	 * @return the results
	 */
	public List<String> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<String> results) {
		this.results = results;
	}


}
