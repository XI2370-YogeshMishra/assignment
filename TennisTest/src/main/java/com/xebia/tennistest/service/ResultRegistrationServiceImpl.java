package com.xebia.tennistest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.tennistest.entity.League;
import com.xebia.tennistest.entity.Match;
import com.xebia.tennistest.exception.NotFoundException;
import com.xebia.tennistest.repository.LeagueRepository;
import com.xebia.tennistest.repository.MatchRepository;
import com.xebia.tennistest.util.BracketPosition;
import com.xebia.tennistest.util.MatchWinner;
import com.xebia.tennistest.service.VictoryConditionsChecker;
import com.xebia.tennistest.validaion.ResultRegistrationForm;

@Service
@Transactional(readOnly = true)
public class ResultRegistrationServiceImpl implements ResultRegistrationService {

    private MatchRepository matchRepository;

    private LeagueRepository leagueRepository;

    private VictoryConditionsChecker victoryConditionsChecker;

    @Autowired
    public ResultRegistrationServiceImpl(MatchRepository matchRepository, LeagueRepository leagueRepository, VictoryConditionsChecker victoryConditionsChecker) {
        this.matchRepository = matchRepository;
        this.leagueRepository = leagueRepository;
        this.victoryConditionsChecker = victoryConditionsChecker;
    }

    @Override
    @Transactional
    public Long registerResults(Long matchId, ResultRegistrationForm resultRegistrationForm) {

        Match match = matchRepository.findById(matchId)
                .orElseThrow( () -> new NotFoundException("Match with id " + matchId + " not found") );

        League league = leagueRepository.findById(match.getCompetitionId())
                .orElseThrow(InternalError::new);

        MatchWinner matchWinner = victoryConditionsChecker.determineWinner(resultRegistrationForm, league.getVictoryConditions());
        match.registerResults(resultRegistrationForm, matchWinner);

        advanceWinnerToNextRound(match, league);

        return match.getId();
    }

    private void advanceWinnerToNextRound(Match match, League league) {
        if(match.getBracketPosition().getRound() == league.getNumberOfRounds()) {
            assignWinnerToCompetition(match, league);
        } else {
            assignWinnerToNextMatch(match);
        }
    }

    private void assignWinnerToCompetition(Match match, League league) {
        Long winningPlayerId = match.getWinningPlayerId()
                .orElseThrow(InternalError::new);
        league.assignWinner(winningPlayerId);
    }

    private void assignWinnerToNextMatch(Match match) {
        BracketPosition nextMatchBracketPosition = new BracketPosition(match.getBracketPosition().getRound()+1, (match.getBracketPosition().getPosition()+1)/2);

        Match nextMatch = matchRepository.findByCompetitionIdAndBracketPosition(match.getCompetitionId(), nextMatchBracketPosition)
                .orElseThrow(InternalError::new);

        Long winningPlayerId = match.getWinningPlayerId()
                .orElseThrow(InternalError::new);

        nextMatch.assignPlayerToSlot(winningPlayerId, ((match.getBracketPosition().getPosition()+1) % 2)+1);

    }


}

