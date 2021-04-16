package com.xebia.tennistest.service;

import com.xebia.tennistest.entity.VictoryConditions;
import com.xebia.tennistest.util.MatchWinner;
import com.xebia.tennistest.validaion.ResultRegistrationForm;

public interface VictoryConditionsChecker {
	 MatchWinner determineWinner(ResultRegistrationForm resultRegistrationForm, VictoryConditions victoryConditions);
}
