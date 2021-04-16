package com.xebia.tennistest.validaion;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.xebia.tennistest.entity.GameRow;


public class ResultRegistrationForm {
	@NotEmpty
    @Valid
    private List<GameRow> games;

    public ResultRegistrationForm() {
        games = new ArrayList<>();
    }

    public List<GameRow> getGames() {
        return games;
    }

    public void setGames(List<GameRow> games) {
        this.games = games;
    }
}
