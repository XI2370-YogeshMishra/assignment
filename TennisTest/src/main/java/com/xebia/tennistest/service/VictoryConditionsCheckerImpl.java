package com.xebia.tennistest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xebia.tennistest.entity.GameRow;
import com.xebia.tennistest.entity.VictoryConditions;
import com.xebia.tennistest.exception.ServiceValidationException;
import com.xebia.tennistest.util.MatchWinner;
import com.xebia.tennistest.validaion.ResultRegistrationForm;
@Component
public class VictoryConditionsCheckerImpl implements VictoryConditionsChecker {

    public VictoryConditionsCheckerImpl() {
    }

    @Override
    public MatchWinner determineWinner(ResultRegistrationForm resultRegistrationForm, VictoryConditions victoryConditions) {

        checkForm(resultRegistrationForm);

        MatchScore matchScore = new MatchScore(resultRegistrationForm.getGames(), victoryConditions.getNumberOfPointsToWin());

        checkNumberOfWins(matchScore.player1Wins, matchScore.player2Wins,victoryConditions.getNumberOfWinsRequired() );

        if(matchScore.player1Wins > matchScore.player2Wins) {
            return MatchWinner.PLAYER1;
        }
        return MatchWinner.PLAYER2;

    }

    private void checkForm(ResultRegistrationForm resultRegistrationForm) {
        List<String> errorList = new ArrayList<>();

        if(resultRegistrationForm == null) {
            errorList.add("No match form provided.");
            throw new ServiceValidationException(errorList);
        }
    }

    private void checkNumberOfWins(Integer player1Wins, Integer player2Wins, Integer numberOfWinsRequired) {
        List<String> errorList = new ArrayList<>();
        if(((player1Wins > player2Wins && player1Wins != numberOfWinsRequired) ||
                (player2Wins > player1Wins  && player2Wins != numberOfWinsRequired)) ||
                (player1Wins == player2Wins) ) {
            errorList.add("Incorrect number of games won.");
            throw new ServiceValidationException(errorList);
        }
    }
}

class MatchScore {

    Integer player1Wins = 0;
    Integer player2Wins = 0;

    public MatchScore(List<GameRow> games, Integer numberOfPointsToWin) {
        List<String> errorList = new ArrayList<>();
        Integer index = 0;
        for(GameRow game : games) {
            index += 1;
            if (((game.getScorePlayer1() > (game.getScorePlayer2() + 1)) && (game.getScorePlayer1() == numberOfPointsToWin)) ||
               ((game.getScorePlayer1() == (game.getScorePlayer2() + 2)) && (game.getScorePlayer1() >= numberOfPointsToWin))) {
                player1Wins += 1;
            } else if (((game.getScorePlayer2() > (game.getScorePlayer1() + 1)) && (game.getScorePlayer2() == numberOfPointsToWin)) ||
                ((game.getScorePlayer2() == (game.getScorePlayer1() + 2)) && (game.getScorePlayer2() >= numberOfPointsToWin))) {
                player2Wins += 1;
            } else {
                errorList.add("Game no. " + index + " has incorrect score.");
            }
        }
        if(!errorList.isEmpty()) {
            throw new ServiceValidationException(errorList);
        }
    }
}
