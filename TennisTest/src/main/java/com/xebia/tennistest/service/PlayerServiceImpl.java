package com.xebia.tennistest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.tennistest.entity.Player;
import com.xebia.tennistest.entity.PlayerForm;
import com.xebia.tennistest.repository.PlayerRepository;

@Service
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {

	private PlayerRepository playerRepository;

	@Autowired
	public PlayerServiceImpl(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	@Transactional
	public Long addPlayer(PlayerForm playerForm) {

		Player player = new Player(playerForm.getFirstName(), playerForm.getLastName());
		Player savedPlayer = playerRepository.save(player);
		return savedPlayer.getId();
	}
}
