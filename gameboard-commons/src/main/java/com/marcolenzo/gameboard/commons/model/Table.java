package com.marcolenzo.gameboard.commons.model;

import java.util.List;

/**
 * When a game session gets crowded, players are split in different tables.
 * @author Marco Lenzo
 *
 */
public class Table {

	private Integer number;

	private List<String> userIds;

	private Result result;

	/**
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}


		/**
	 * @return the userIds
	 */
	public List<String> getUserIds() {
		return userIds;
	}

	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}


}
