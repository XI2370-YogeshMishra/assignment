package com.xebia.tennistest.service;

import java.util.Collection;

import com.xebia.tennistest.entity.League;
import com.xebia.tennistest.entity.LeagueForm;

public interface LeagueService {
	 Long createCompetition(LeagueForm leagueForm);
	 Long setWinner(long winningplayerid);
	 Collection<League> getAllLeague();
}
