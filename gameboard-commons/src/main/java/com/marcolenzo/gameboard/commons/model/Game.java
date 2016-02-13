package com.marcolenzo.gameboard.commons.model;

import java.time.LocalDateTime;

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


}
