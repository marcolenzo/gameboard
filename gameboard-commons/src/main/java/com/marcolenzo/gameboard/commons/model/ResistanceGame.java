package com.marcolenzo.gameboard.commons.model;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ResistanceGame extends Game {

	@NotNull
	private Boolean resistanceWin;

	@NotEmpty
	private Set<String> players;
	
	@NotEmpty
	private Set<String> spies;

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

}
