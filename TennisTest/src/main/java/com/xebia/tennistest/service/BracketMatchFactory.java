package com.xebia.tennistest.service;

import java.util.List;
import java.util.Map;

import com.xebia.tennistest.entity.League;
import com.xebia.tennistest.entity.Match;
import com.xebia.tennistest.util.BracketPosition;

public interface BracketMatchFactory {
	  Map<BracketPosition, Match> generateMatches(League league, List<Long> playerIds);
}
