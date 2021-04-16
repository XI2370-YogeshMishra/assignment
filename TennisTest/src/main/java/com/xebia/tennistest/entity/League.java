package com.xebia.tennistest.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.xebia.tennistest.util.Calculator;
@Entity
public class League {
	@Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer numberOfPlayers;

    private Integer numberOfRounds;

    private Integer numberOfMatchesInFirstRound;

    @Embedded
    private VictoryConditions victoryConditions;

    private Long winningPlayerId;

    public League() {
    }

    public League(String name, Integer numberOfPlayers, Integer numberOfWinsRequired, Integer numberOfPointsToWin) {

        this.name = name;
        this.victoryConditions = new VictoryConditions(numberOfWinsRequired, numberOfPointsToWin);
        calculateRounds(numberOfPlayers);
    }

    private void calculateRounds(Integer noOfPlayers) {
        numberOfPlayers = noOfPlayers;
        numberOfRounds = Calculator.log2ceil(numberOfPlayers);
        numberOfMatchesInFirstRound = numberOfPlayers - Calculator.pow2N(numberOfRounds - 1);
    }

    public Integer calculateMatchesInRound(Integer round) {
        if (round == 1) {
            return numberOfMatchesInFirstRound;
        }
        return Calculator.pow2N(numberOfRounds - round);
    }

    public void assignWinner(Long playerId) {
        this.winningPlayerId = playerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Integer getNumberOfRounds() {
        return numberOfRounds;
    }

    public Integer getNumberOfMatchesInFirstRound() {
        return numberOfMatchesInFirstRound;
    }

    public Long getWinningPlayerId() {
        return winningPlayerId;
    }

    public VictoryConditions getVictoryConditions() {
        return victoryConditions;
    }
}
