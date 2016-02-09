package com.marcolenzo.gameboard.commons.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RecurringGame {

	@Id
	private String id;

	@Indexed
	private String boardId;

	private LocalDateTime startTime;

	private Boolean everyDay;

	private Boolean everyMonday;

	private Boolean everyTuesday;

	private Boolean everyWednesday;

	private Boolean everyThursday;

	private Boolean everyFriday;

	private Boolean everySaturday;

	private Boolean everySunday;

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
	 * @return the everyDay
	 */
	public Boolean getEveryDay() {
		return everyDay;
	}

	/**
	 * @param everyDay the everyDay to set
	 */
	public void setEveryDay(Boolean everyDay) {
		this.everyDay = everyDay;
	}

	/**
	 * @return the everyMonday
	 */
	public Boolean getEveryMonday() {
		return everyMonday;
	}

	/**
	 * @param everyMonday the everyMonday to set
	 */
	public void setEveryMonday(Boolean everyMonday) {
		this.everyMonday = everyMonday;
	}

	/**
	 * @return the everyTuesday
	 */
	public Boolean getEveryTuesday() {
		return everyTuesday;
	}

	/**
	 * @param everyTuesday the everyTuesday to set
	 */
	public void setEveryTuesday(Boolean everyTuesday) {
		this.everyTuesday = everyTuesday;
	}

	/**
	 * @return the everyWednesday
	 */
	public Boolean getEveryWednesday() {
		return everyWednesday;
	}

	/**
	 * @param everyWednesday the everyWednesday to set
	 */
	public void setEveryWednesday(Boolean everyWednesday) {
		this.everyWednesday = everyWednesday;
	}

	/**
	 * @return the everyThursday
	 */
	public Boolean getEveryThursday() {
		return everyThursday;
	}

	/**
	 * @param everyThursday the everyThursday to set
	 */
	public void setEveryThursday(Boolean everyThursday) {
		this.everyThursday = everyThursday;
	}

	/**
	 * @return the everyFriday
	 */
	public Boolean getEveryFriday() {
		return everyFriday;
	}

	/**
	 * @param everyFriday the everyFriday to set
	 */
	public void setEveryFriday(Boolean everyFriday) {
		this.everyFriday = everyFriday;
	}

	/**
	 * @return the everySaturday
	 */
	public Boolean getEverySaturday() {
		return everySaturday;
	}

	/**
	 * @param everySaturday the everySaturday to set
	 */
	public void setEverySaturday(Boolean everySaturday) {
		this.everySaturday = everySaturday;
	}

	/**
	 * @return the everySunday
	 */
	public Boolean getEverySunday() {
		return everySunday;
	}

	/**
	 * @param everySunday the everySunday to set
	 */
	public void setEverySunday(Boolean everySunday) {
		this.everySunday = everySunday;
	}

}
