package com.xebia.tennistest.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



public class GameRow {
	@NotNull
    @Min(value = 0)
    private Integer scorePlayer1;

    @NotNull
    @Min(value = 0)
    private Integer scorePlayer2;

    public GameRow(Integer scorePlayer1, Integer scorePlayer2) {
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
    }

    public Integer getScorePlayer1() {
        return scorePlayer1;
    }

    public void setScorePlayer1(Integer scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public Integer getScorePlayer2() {
        return scorePlayer2;
    }

    public void setScorePlayer2(Integer scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }
}


