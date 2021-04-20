package com.xebia.tennistest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xebia.tennistest.entity.LeagueGroup;
import com.xebia.tennistest.entity.LeagueForm;
import com.xebia.tennistest.entity.Player;
import com.xebia.tennistest.entity.PlayerForm;
import com.xebia.tennistest.exception.NotFoundException;
import com.xebia.tennistest.repository.GroupRepository;
import com.xebia.tennistest.repository.PlayerRepository;
import com.xebia.tennistest.service.LeagueService;
import com.xebia.tennistest.service.PlayerService;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

	private PlayerRepository playerRepository;
	@Autowired
	private GroupRepository groupRepository;

	private PlayerService playerService;
	@Autowired
	private LeagueService leagueService;

	@Autowired
	public PlayerController(PlayerRepository playerRepository, PlayerService playerService) {
		this.playerRepository = playerRepository;
		this.playerService = playerService;
	}

	@GetMapping
	public Collection<Player> getPlayers() {

		return playerRepository.findAll();
	}

	@GetMapping("/{playerId}")
	public Player player(@PathVariable("playerId") long playerId) {

		return playerRepository.findById(playerId)
				.orElseThrow(() -> new NotFoundException("Player with id " + playerId + " not found"));
	}

	@GetMapping("match/{matchId}")
	public Map<Long, Player> getPlayersByMatchId(@PathVariable("matchId") Long matchId) {

		return playerRepository.findAllByMatchId(matchId).stream().collect(Collectors.toMap(Player::getId, v -> v));
	}

	@GetMapping("league/{leagueId}")
	public Map<Long, Player> getPlayersByCompetitionId(@PathVariable("leagueId") Long competitionId) {

		return playerRepository.findAllByCompetitionId(competitionId).stream()
				.collect(Collectors.toMap(Player::getId, v -> v));
	}

	@PostMapping("/add")
	public ResponseEntity<?> processPlayerForm(@Valid @RequestBody PlayerForm playerForm) {

		Long id = playerService.addPlayer(playerForm);

		List<Long> listIds = listIds();
		List<Long> playerIdList = new ArrayList<>();
		playerIdList.addAll(listIds);

		LeagueForm leagueForm = new LeagueForm();
		leagueForm.setName("League");
		leagueForm.setPlayerIds(playerIdList);
		leagueForm.setNumberOfWinsRequired(3);
		leagueForm.setNumberOfPointsToWin(11);
		leagueService.createCompetition(leagueForm);

		HttpHeaders responseHeader = new HttpHeaders();
		return new ResponseEntity<>(id, responseHeader, HttpStatus.CREATED);

	}
	public List<Long> listIds() {
		return playerRepository.findAll().stream().map(Player -> Player.getId()).collect(Collectors.toList());
	}
	@PutMapping("/group/{groupcount}")
	public void createGroup(@PathVariable("groupcount") int groupcount ) {
		List<Long> listIds = listIds();
		playerService.createGroup(groupcount,listIds);
	}
	
}
