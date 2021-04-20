package com.xebia.tennistest.service;

import com.xebia.tennistest.entity.LeagueForm;

public interface LeagueService {
	 Long createCompetition(LeagueForm leagueForm);
	 Long setWinner(long winningplayerid);
}
