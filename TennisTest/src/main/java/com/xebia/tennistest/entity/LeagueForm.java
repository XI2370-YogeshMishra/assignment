package com.xebia.tennistest.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class LeagueForm {

    @Size(min = 6, max = 32, message = "League name must be between {min} and {max} characters long")
    private String name;

    @Size(min = 4, max = 32, message = "League must have between {min} and {max} participants")
    private List<Long> playerIds = new ArrayList<>();

    @Min(value = 1, message = "Number of games to win match must be {value} or greater")
    @Max(value = 5, message = "Number of games to win match must be {value} or less")
    private Integer numberOfWinsRequired;

    @Min(value = 11, message = "Number of points to win game must be {value} or greater")
    @Max(value = 25, message = "Number of points to win game must be {value} or less")
    private Integer numberOfPointsToWin;

    public LeagueForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }

    public Integer getNumberOfWinsRequired() {
        return numberOfWinsRequired;
    }

    public void setNumberOfWinsRequired(Integer numberOfWinsRequired) {
        this.numberOfWinsRequired = numberOfWinsRequired;
    }

    public Integer getNumberOfPointsToWin() {
        return numberOfPointsToWin;
    }

    public void setNumberOfPointsToWin(Integer numberOfPointsToWin) {
        this.numberOfPointsToWin = numberOfPointsToWin;
    }

}
