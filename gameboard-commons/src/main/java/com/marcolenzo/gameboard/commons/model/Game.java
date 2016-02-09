package com.marcolenzo.gameboard.commons.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A planned game session where multiple tables and multiple games can be setup.
 * @author Marco Lenzo
 *
 */
@Document
public class Game {

	@Id
	private String id;

	@Indexed
	private String boardId;

	private LocalDateTime startTime;

	private Status status;

	private List<Table> tables;

	private List<String> registeredUsers;
	

	public enum Status {
		ACCEPTING_REGISTRATIONS, REGISTRATIONS_CLOSED, AWAITING_START, IN_PROGRESS, AWAITING_RESULT, DONE;
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
	 * @return the tables
	 */
	public List<Table> getTables() {
		return tables;
	}


		/**
	 * @param tables the tables to set
	 */
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}


		/**
	 * @return the registeredUsers
	 */
	public List<String> getRegisteredUsers() {
		return registeredUsers;
	}


		/**
	 * @param registeredUsers the registeredUsers to set
	 */
	public void setRegisteredUsers(List<String> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}


}
