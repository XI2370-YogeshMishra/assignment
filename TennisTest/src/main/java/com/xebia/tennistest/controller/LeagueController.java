package com.xebia.tennistest.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.tennistest.entity.League;
import com.xebia.tennistest.entity.LeagueForm;
import com.xebia.tennistest.service.EmailService;
import com.xebia.tennistest.service.LeagueService;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {
	@Autowired
	private EmailService emailService;

	private LeagueService leagueService;

	@Autowired
	public LeagueController(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

	@GetMapping
	public Collection<League> getCompetitions() {
		// return leagueRepository.findAll();
		return leagueService.getAllLeague();
	}

	@PostMapping("/add")
	public ResponseEntity<?> processCompetitionForm(@Valid @RequestBody LeagueForm leagueForm) {
		Long id = leagueService.createCompetition(leagueForm);
		HttpHeaders responseHeader = new HttpHeaders();
		return new ResponseEntity<>(id, responseHeader, HttpStatus.CREATED);
	}

	@PutMapping("/{winningPlayerId}")
	public void setWinner(@PathVariable("winningPlayerId") long winningPlayerId) {
		Long id = leagueService.setWinner(winningPlayerId);
		emailService.sendMail("winner@gmail.com", "Winner", "You won the championShip");
	}

}
