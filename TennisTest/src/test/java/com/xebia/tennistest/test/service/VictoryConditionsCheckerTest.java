package com.xebia.tennistest.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xebia.tennistest.entity.GameRow;
import com.xebia.tennistest.entity.VictoryConditions;
import com.xebia.tennistest.exception.ServiceValidationException;
import com.xebia.tennistest.service.ResultRegistrationService;
import com.xebia.tennistest.service.VictoryConditionsChecker;
import com.xebia.tennistest.util.MatchWinner;
import com.xebia.tennistest.validaion.ResultRegistrationForm;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class VictoryConditionsCheckerTest {

    @Autowired
    VictoryConditionsChecker victoryConditionsChecker;
    

    @Test
    public void shouldSucceedWhenCorrectMatchFormProvided() throws Exception {
        List<GameRow> gameList = new ArrayList<>();
        gameList.add(new GameRow(11, 2));
        gameList.add(new GameRow(9, 11));
        gameList.add(new GameRow(13, 11));
        gameList.add(new GameRow(10, 12));
        gameList.add(new GameRow(11, 0));

        ResultRegistrationForm resultRegistrationForm = new ResultRegistrationForm();
        resultRegistrationForm.setGames(gameList);
        VictoryConditions victoryConditions = new VictoryConditions(3, 11);
        MatchWinner matchWinner = victoryConditionsChecker.determineWinner(resultRegistrationForm, victoryConditions);
        assertThat(matchWinner).isEqualTo(MatchWinner.PLAYER1);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldFailWhenNoMatchFormProvided() throws Exception {
        ResultRegistrationForm resultRegistrationForm = null;
        VictoryConditions victoryConditions = new VictoryConditions(3, 11);
        victoryConditionsChecker.determineWinner(resultRegistrationForm, victoryConditions);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldFailWhenMatchFormWithWrongGameResultsProvided() throws Exception {
     
        List<GameRow> gameList = new ArrayList<>();
        gameList.add(new GameRow(10, 2));
        gameList.add(new GameRow(11, 11));
        gameList.add(new GameRow(13, 13));
        gameList.add(new GameRow(10, 28));
        gameList.add(new GameRow(0, 0));

        ResultRegistrationForm resultRegistrationForm = new ResultRegistrationForm();
        resultRegistrationForm.setGames(gameList);

        VictoryConditions victoryConditions = new VictoryConditions(3, 11);
        victoryConditionsChecker.determineWinner(resultRegistrationForm, victoryConditions);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldFailWhenMatchFormWithWrongGameResultsProvided2() throws Exception {

        List<GameRow> gameList = new ArrayList<>();
        gameList.add(new GameRow(12, 8));
        gameList.add(new GameRow(12, 8));
        gameList.add(new GameRow(12, 8));

        ResultRegistrationForm resultRegistrationForm = new ResultRegistrationForm();
        resultRegistrationForm.setGames(gameList);
        VictoryConditions victoryConditions = new VictoryConditions(3, 11);
        victoryConditionsChecker.determineWinner(resultRegistrationForm, victoryConditions);
    }


    @Test(expected = ServiceValidationException.class)
    public void shouldFailWhenMatchFormWithWrongGameNumberProvided() throws Exception {
        List<GameRow> gameList = new ArrayList<>();
        gameList.add(new GameRow(11, 2));
        gameList.add(new GameRow(9, 11));
        gameList.add(new GameRow(1, 11));
        gameList.add(new GameRow(12, 10));

        ResultRegistrationForm resultRegistrationForm = new ResultRegistrationForm();
        resultRegistrationForm.setGames(gameList);

        VictoryConditions victoryConditions = new VictoryConditions(3, 11);
        victoryConditionsChecker.determineWinner(resultRegistrationForm, victoryConditions);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldFailWhenMatchFormWithSameNumberOfWinsProvided() throws Exception {
  
        List<GameRow> gameList = new ArrayList<>();
        gameList.add(new GameRow(11, 2));
        gameList.add(new GameRow(2, 11));
        gameList.add(new GameRow(11, 2));
        gameList.add(new GameRow(2, 11));
        gameList.add(new GameRow(11, 2));
        gameList.add(new GameRow(2, 11));

        ResultRegistrationForm resultRegistrationForm = new ResultRegistrationForm();
        resultRegistrationForm.setGames(gameList);

        VictoryConditions victoryConditions = new VictoryConditions(3, 11);
        victoryConditionsChecker.determineWinner(resultRegistrationForm, victoryConditions);
    }
   
}