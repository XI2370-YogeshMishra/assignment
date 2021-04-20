package com.xebia.tennistest.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

public class LeagueGroup {
	@Id
	@GeneratedValue
	private Long id;
	@ElementCollection
	@CollectionTable(name = "listOfUsers")
	private List<Long> PlayerID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param playerID
	 */
	public LeagueGroup(List<Long> playerID) {
		super();
		PlayerID = playerID;
	}

	public List<Long> getPlayerID() {
		return PlayerID;
	}

	public void setPlayerID(List<Long> playerID) {
		PlayerID = playerID;
	}

	public LeagueGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

}
