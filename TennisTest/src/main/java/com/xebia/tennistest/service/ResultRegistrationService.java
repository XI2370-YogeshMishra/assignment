package com.xebia.tennistest.service;

import com.xebia.tennistest.validaion.ResultRegistrationForm;

public interface ResultRegistrationService {

    Long registerResults(Long matchId, ResultRegistrationForm resultRegistrationForm);

}