package com.xebia.tennistest.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xebia.tennistest.entity.Player;
import com.xebia.tennistest.entity.PlayerForm;

public interface PlayerService {
	Collection<Player> getAllPlayer();
	Player findById(Long playerId);
	Map<Long, Player> getAllByMatchId( Long matchId);
	Map<Long, Player> getPlayerByLeagueId(Long leagueId);
	 List<Long> getAllPlayerIds();
	Long addPlayer(PlayerForm playerForm);

	void createGroup(int groupcount, List<Long> listIds);
}
