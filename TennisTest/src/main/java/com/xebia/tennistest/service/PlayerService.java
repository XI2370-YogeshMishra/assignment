package com.xebia.tennistest.service;

import java.util.List;

import com.xebia.tennistest.entity.PlayerForm;

public interface PlayerService {

	Long addPlayer(PlayerForm playerForm);

	void createGroup(int groupcount, List<Long> listIds);
}
