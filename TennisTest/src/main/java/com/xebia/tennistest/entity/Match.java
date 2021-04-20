package com.xebia.tennistest.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.xebia.tennistest.exception.ServiceValidationException;
import com.xebia.tennistest.util.BracketPosition;
import com.xebia.tennistest.util.MatchStatus;
import com.xebia.tennistest.util.MatchWinner;
import com.xebia.tennistest.validaion.ResultRegistrationForm;

@Entity
public class Match {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(updatable = false)
    private Long competitionId;
    private Long player1Id;
    private Long player2Id;
    private boolean matchclosestatus;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Game> games;

    @Embedded
    private BracketPosition bracketPosition;

    private LocalDateTime resultRegistrationTime;

    private MatchWinner matchWinner;

    public Match() {
        this.matchWinner = MatchWinner.UNKNOWN;
    }

    public Match(String name, Long competitionId, Integer round, Integer position) {
        this.name = name;
        this.competitionId = competitionId;
        this.bracketPosition = new BracketPosition(round, position);
        this.matchWinner = MatchWinner.UNKNOWN;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPlayer1Id() {
        return player1Id;
    }

    public Long getPlayer2Id() {
        return player2Id;
    }

    public void assignPlayerToSlot(Long playerId, Integer slot) {
        if(slot == 1) {
            this.player1Id = playerId;
        } else if (slot == 2) {
            this.player2Id = playerId;
        }
    }

    public Collection<Game> getGames() {
        return games;
    }

    public BracketPosition getBracketPosition() {
        return bracketPosition;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public LocalDateTime getResultRegistrationTime() {
        return resultRegistrationTime;
    }

    public MatchWinner getMatchWinner() {
        return matchWinner;
    }
    


	public boolean isMatchclosestatus() {
		return matchclosestatus;
	}

	public void setMatchclosestatus(boolean matchclosestatus) {
		this.matchclosestatus = matchclosestatus;
	}

	public Optional<Long> getWinningPlayerId() {
        if(matchWinner == MatchWinner.PLAYER1) {
            return Optional.ofNullable(player1Id);
        } else if (matchWinner == MatchWinner.PLAYER2) {
            return Optional.ofNullable(player2Id);
        } else {
            return Optional.empty();
        }
    }

    public MatchStatus getStatus() {
    	if(matchclosestatus==true) {
    		return  MatchStatus.CLOSE;
    	}
    	else if(resultRegistrationTime != null) {
            return MatchStatus.CONCLUDED;
        } else if (player1Id != null && player2Id != null) {
            return MatchStatus.READY;
        } else {
            return MatchStatus.NOT_READY;
        }
    }

    public void registerResults(ResultRegistrationForm resultRegistrationForm, MatchWinner matchWinner) {

        validateMatchPlayers();
        validateMatchRegistrationStatus();

        games.clear();
        Integer i = 1;
        for(GameRow game : resultRegistrationForm.getGames()) {
            Game newGame = new Game(i, game.getScorePlayer1(), game.getScorePlayer2());
            games.add(newGame);
            i++;
        }
        resultRegistrationTime = LocalDateTime.now();
        this.matchWinner = matchWinner;
    }

    private void validateMatchPlayers() {
        if(player1Id == null || player2Id == null) {
            List<String> errorList = new ArrayList<>();
            errorList.add("Cannot register results when both players are not assigned to the match.");
            throw new ServiceValidationException(errorList);
        }
    }

    private void validateMatchRegistrationStatus() {
        if(resultRegistrationTime != null) {
            List<String> errorList = new ArrayList<>();
            errorList.add("Cannot register results when results already registered.");
            throw new ServiceValidationException(errorList);
        }
    }
}

