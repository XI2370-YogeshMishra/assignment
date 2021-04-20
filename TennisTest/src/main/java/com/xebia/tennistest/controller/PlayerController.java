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

	private PlayerService playerService;
	private LeagueService leagueService;

	@Autowired
	public PlayerController(LeagueService leagueService, PlayerService playerService) {
		this.playerService = playerService;
		this.leagueService=leagueService;
	}

	@GetMapping
	public Collection<Player> getPlayers() {

		return playerService.getAllPlayer();
	}

	@GetMapping("/{playerId}")
	public Player player(@PathVariable("playerId") long playerId) {

		return playerService.findById(playerId);
	}

	@GetMapping("match/{matchId}")
	public Map<Long, Player> getPlayersByMatchId(@PathVariable("matchId") Long matchId) {

		return playerService.getAllByMatchId(matchId);
	}

	@GetMapping("league/{leagueId}")
	public Map<Long, Player> getPlayersByCompetitionId(@PathVariable("leagueId") Long leagueId) {

		return playerService.getPlayerByLeagueId(leagueId);
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
		return playerService.getAllPlayerIds();
	}

	@PutMapping("/group/{groupcount}")
	public void createGroup(@PathVariable("groupcount") int groupcount) {
		List<Long> listIds = listIds();
		playerService.createGroup(groupcount, listIds);
	}

}
