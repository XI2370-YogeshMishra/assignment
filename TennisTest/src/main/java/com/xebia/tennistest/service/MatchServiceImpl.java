package com.xebia.tennistest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xebia.tennistest.entity.Match;
import com.xebia.tennistest.exception.NotFoundException;
import com.xebia.tennistest.repository.MatchRepository;
@Service
public class MatchServiceImpl implements MatchService {

	private MatchRepository matchRepository;

	@Autowired
	public MatchServiceImpl(MatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}

	@Override
	public Collection<Match> getAllMatches() {
		return matchRepository.findAll();

	}

	@Override
	public Match findById(Long matchId) {
		return matchRepository.findById(matchId)
				.orElseThrow(() -> new NotFoundException("Match with id " + matchId + " not found"));
	}

	@Override
	public void closeMatch(Long matchId) {
		matchRepository.closeMatch(matchId);
		
	}

}
