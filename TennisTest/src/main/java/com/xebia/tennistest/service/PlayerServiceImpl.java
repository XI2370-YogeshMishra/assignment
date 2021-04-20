package com.xebia.tennistest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xebia.tennistest.entity.LeagueGroup;
import com.xebia.tennistest.entity.Player;
import com.xebia.tennistest.entity.PlayerForm;
import com.xebia.tennistest.exception.NotFoundException;
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
		if (listIds.size() % groupcount != 0) {
			divide++;
		}
		List<List<Long>> partition = MyPartition.partition(listIds, divide);
		for (List<Long> list1 : partition) {
			groupRepository.save(new LeagueGroup(list1));
		}

	}

	@Override
	public Collection<Player> getAllPlayer() {
		return playerRepository.findAll();
	}

	@Override
	public Player findById(Long playerId) {
		return playerRepository.findById(playerId)
				.orElseThrow(() -> new NotFoundException("Player with id " + playerId + " not found"));
	}

	@Override
	public Map<Long, Player> getAllByMatchId(Long matchId) {
		return playerRepository.findAllByMatchId(matchId).stream().collect(Collectors.toMap(Player::getId, v -> v));
	}

	@Override
	public Map<Long, Player> getPlayerByLeagueId(Long leagueId) {
		return playerRepository.findAllByCompetitionId(leagueId).stream()
				.collect(Collectors.toMap(Player::getId, v -> v));
	}

	@Override
	public List<Long> getAllPlayerIds() {
		return playerRepository.findAll().stream().map(Player -> Player.getId()).collect(Collectors.toList());
	}
}
