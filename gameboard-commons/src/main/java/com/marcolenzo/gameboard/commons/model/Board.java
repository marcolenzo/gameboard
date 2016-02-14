package com.marcolenzo.gameboard.commons.model;

import java.util.Map;
import java.util.Set;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

/**
 * A Gameboard allows group of friends to organize their game sessions and keep track of their performance.
 * @author Marco Lenzo
 *
 */
@Document
public class Board {

	@Id
	private String id;

	@NotEmpty
	@Size(min = 1, max = 64)
	@Indexed(unique = true)
	private String name;
	
	private String type = "RESISTANCE";

	private Set<String> admins;

	private Set<BoardPlayer> players;
	
	@JsonIgnore
	@Transient
	private Map<String, BoardPlayer> playersMap;


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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the admins
	 */
	public Set<String> getAdmins() {
		return admins;
	}

	/**
	 * @param admins the admins to set
	 */
	public void setAdmins(Set<String> admins) {
		this.admins = admins;
	}

	/**
	 * @return the players
	 */
	public Set<BoardPlayer> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(Set<BoardPlayer> players) {
		this.players = players;
	}

	/**
	 * @return A players map indexed by user ID.
	 */
	public Map<String, BoardPlayer> getPlayersMap() {
		if (playersMap == null) {
			playersMap = Maps.newHashMap();
			for (BoardPlayer player : getPlayers()) {
				playersMap.put(player.getUserId(), player);
			}
		}
		return playersMap;
	}


}
