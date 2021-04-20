package com.xebia.tennistest.service;

import java.util.Collection;

import com.xebia.tennistest.entity.Match;

public interface MatchService {
	Collection<Match> getAllMatches();
	Match findById(Long matchId);
	void closeMatch( Long matchId);
}
