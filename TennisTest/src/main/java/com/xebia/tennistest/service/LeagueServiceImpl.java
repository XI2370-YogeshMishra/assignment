package com.xebia.tennistest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.tennistest.entity.League;
import com.xebia.tennistest.entity.LeagueForm;
import com.xebia.tennistest.entity.Match;
import com.xebia.tennistest.entity.Player;
import com.xebia.tennistest.exception.ServiceValidationException;
import com.xebia.tennistest.repository.LeagueRepository;
import com.xebia.tennistest.repository.MatchRepository;
import com.xebia.tennistest.repository.PlayerRepository;
import com.xebia.tennistest.util.BracketPosition;

@Service
@Transactional(readOnly = true)
public class LeagueServiceImpl implements LeagueService {

	private LeagueRepository leagueRepository;

	private PlayerRepository playerRepository;

	private MatchRepository matchRepository;

	private BracketMatchFactory bracketMatchFactory;

	@Autowired
	public LeagueServiceImpl(LeagueRepository leagueRepository, PlayerRepository playerRepository,
			MatchRepository matchRepository, BracketMatchFactory bracketMatchFactory) {
		this.leagueRepository = leagueRepository;
		this.playerRepository = playerRepository;
		this.matchRepository = matchRepository;
		this.bracketMatchFactory = bracketMatchFactory;
	}

	@Override
	@Transactional
	public Long createCompetition(LeagueForm leagueForm) {

		List<Long> playerIds = leagueForm.getPlayerIds();
		validatePlayerIds(playerIds);
		Collections.shuffle(playerIds);

		League league = leagueRepository.save(new League(leagueForm.getName(),
				playerIds.size(), leagueForm.getNumberOfWinsRequired(), leagueForm.getNumberOfPointsToWin()));

		Map<BracketPosition, Match> matches = bracketMatchFactory.generateMatches(league, playerIds);

		matchRepository.saveAll(matches.values());

		return league.getId();
	}

	private void validatePlayerIds(List<Long> playerIds) {
		List<String> errorList = new ArrayList<>();

		List<Player> players = playerRepository.findByIdIn(playerIds);
		if (players.size() != playerIds.size()) {
			errorList.add("Invalid player list provided.");
			throw new ServiceValidationException(errorList);
		}
	}

	@Override
	public Long setWinner(long winningPlayerId) {
		long id=leagueRepository.setWinner(winningPlayerId);
		return null;
	}

	@Override
	public Collection<League> getAllLeague() {
		return  leagueRepository.findAll();
	}


}
