package com.xebia.tennistest.entity;

public class VictoryConditions {
	private Integer numberOfWinsRequired;

    private Integer numberOfPointsToWin;

    public VictoryConditions() {
    }

    public VictoryConditions(Integer numberOfWinsRequired, Integer numberOfPointsToWin) {
        this.numberOfWinsRequired = numberOfWinsRequired;
        this.numberOfPointsToWin = numberOfPointsToWin;
    }

    public Integer getNumberOfWinsRequired() {
        return numberOfWinsRequired;
    }

    public Integer getNumberOfPointsToWin() {
        return numberOfPointsToWin;
    }
}
