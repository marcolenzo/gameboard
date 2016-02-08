package com.marcolenzo.gameboard.commons.model;

import java.util.List;

/**
 * When a game session gets crowded, players are split in different tables.
 * @author Marco Lenzo
 *
 */
public class Table {

	private String id;

	private String gameSessionId;

	private List<User> users;

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
	 * @return the gameSessionId
	 */
	public String getGameSessionId() {
		return gameSessionId;
	}

	/**
	 * @param gameSessionId the gameSessionId to set
	 */
	public void setGameSessionId(String gameSessionId) {
		this.gameSessionId = gameSessionId;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

}
