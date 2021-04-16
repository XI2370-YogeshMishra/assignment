package com.xebia.tennistest.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.xebia.tennistest.entity.League;
import com.xebia.tennistest.entity.Match;
import com.xebia.tennistest.service.BracketMatchFactory;
import com.xebia.tennistest.util.BracketPosition;
import com.xebia.tennistest.util.Calculator;

@Component
public class BracketMatchFactoryImpl implements BracketMatchFactory {

	@Override
	public Map<BracketPosition, Match> generateMatches(League league, List<Long> playerIds) {
		Map<BracketPosition, Match> matches = new HashMap<>();

		for (Integer round = 1; round <= league.getNumberOfRounds(); round++) {
			generateRoundMatches(matches, round, league);
		}

		assignPlayersToMatches(league, playerIds, matches);
		return matches;
	}

	private void generateRoundMatches(Map<BracketPosition, Match> matches, Integer round, League league) {

		Integer matchesInRound = league.calculateMatchesInRound(round);

		for (Integer match = 1; match <= matchesInRound; match++) {
			String name = "1/" + Calculator.pow2N(league.getNumberOfRounds() - round).toString() + "-final no. "
					+ match.toString();
			matches.put(new BracketPosition(round, match), new Match(name, league.getId(), round, match));
		}
	}

	void assignPlayersToMatches(League league, List<Long> playerIds, Map<BracketPosition, Match> matches) {
		for (Integer playerNumber = 0; playerNumber < playerIds.size(); playerNumber++) {

			BracketPosition startingPosition = calculateStartingBracketPosition(league, playerNumber);
			Integer startingSlot = calculateStartingPlayerSlot(league, playerNumber);
			matches.get(startingPosition).assignPlayerToSlot(playerIds.get(playerNumber), startingSlot);

		}

	}

	private BracketPosition calculateStartingBracketPosition(League league, Integer playerNumber) {

		if (playerNumber < 2 * league.getNumberOfMatchesInFirstRound()) {
			return new BracketPosition(1, (playerNumber / 2) + 1);
		} else {
			return new BracketPosition(2, ((playerNumber - league.getNumberOfMatchesInFirstRound()) / 2) + 1);
		}
	}

	private Integer calculateStartingPlayerSlot(League league, Integer playerNumber) {
		if (playerNumber < 2 * league.getNumberOfMatchesInFirstRound()) {
			return playerNumber % 2 + 1;
		} else {
			return (playerNumber - league.getNumberOfMatchesInFirstRound()) % 2 + 1;
		}
	}

}
