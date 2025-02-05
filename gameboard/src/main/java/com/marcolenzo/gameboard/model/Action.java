package com.marcolenzo.gameboard.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents an action performed by a user in the gameboard application.
 * This class is used to log and store details of user actions.
 */
@Document
public class Action {

	@Id
	private String id;

	private String name;

	private List<String> args;

	private String result;

	private String userId;

	private LocalDateTime datetime;

	/**
	 * Gets the ID of the action.
	 *
	 * @return the ID of the action
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the ID of the action.
	 *
	 * @param id the ID to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the name of the action.
	 *
	 * @return the name of the action
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the action.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the arguments of the action.
	 *
	 * @return the arguments of the action
	 */
	public List<String> getArgs() {
		return args;
	}

	/**
	 * Sets the arguments of the action.
	 *
	 * @param args the arguments to set
	 */
	public void setArgs(List<String> args) {
		this.args = args;
	}

	/**
	 * Gets the user ID associated with the action.
	 *
	 * @return the user ID associated with the action
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user ID associated with the action.
	 *
	 * @param userId the user ID to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the datetime when the action was performed.
	 *
	 * @return the datetime when the action was performed
	 */
	public LocalDateTime getDatetime() {
		return datetime;
	}

	/**
	 * Sets the datetime when the action was performed.
	 *
	 * @param datetime the datetime to set
	 */
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	/**
	 * Gets the result of the action.
	 *
	 * @return the result of the action
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets the result of the action.
	 *
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

}
