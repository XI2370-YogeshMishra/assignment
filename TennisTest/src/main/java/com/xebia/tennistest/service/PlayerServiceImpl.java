package com.xebia.tennistest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.tennistest.entity.LeagueGroup;
import com.xebia.tennistest.entity.Player;
import com.xebia.tennistest.entity.PlayerForm;
import com.xebia.tennistest.repository.GroupRepository;
import com.xebia.tennistest.repository.PlayerRepository;
import com.xebia.tennistest.util.MyPartition;

@Service
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {

	private PlayerRepository playerRepository;
	private GroupRepository groupRepository;

	@Autowired
	public PlayerServiceImpl(PlayerRepository playerRepository, GroupRepository groupRepository) {
		this.playerRepository = playerRepository;
		this.groupRepository = groupRepository;
	}

	@Override
	@Transactional
	public Long addPlayer(PlayerForm playerForm) {

		Player player = new Player(playerForm.getFirstName(), playerForm.getLastName());
		Player savedPlayer = playerRepository.save(player);
		return savedPlayer.getId();
	}

	@Override
	@Transactional
	public void createGroup(int groupcount, List<Long> listIds) {
		groupRepository.deleteAll();
		Collections.shuffle(listIds);
		int divide = listIds.size() / groupcount;
		System.out.println("Result " + divide);
		if (listIds.size() % groupcount != 0) {
			divide++;
		}
		System.out.println("Max. number of element in each bucket " + divide);
		List<List<Long>> partition = MyPartition.partition(listIds, divide);
		for (List<Long> list1 : partition) {
			groupRepository.save(new LeagueGroup(list1));
		}

	}
}
