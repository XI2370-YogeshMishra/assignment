package com.xebia.tennistest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xebia.tennistest.entity.Match;
import com.xebia.tennistest.exception.NotFoundException;
import com.xebia.tennistest.repository.MatchRepository;
import com.xebia.tennistest.service.ResultRegistrationService;
import com.xebia.tennistest.util.BracketPosition;
import com.xebia.tennistest.validaion.ResultRegistrationForm;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

	MatchRepository matchRepository;

	ResultRegistrationService resultRegistrationService;

	@Autowired
	public MatchController(MatchRepository matchRepository, ResultRegistrationService resultRegistrationService) {
		this.matchRepository = matchRepository;
		this.resultRegistrationService = resultRegistrationService;
	}

	@GetMapping
	public Collection<Match> getMatches() {

		return matchRepository.findAll();
	}

	@GetMapping("/{matchId}")
	public Match match(@PathVariable("matchId") Long matchId) {

		return matchRepository.findById(matchId)
				.orElseThrow(() -> new NotFoundException("Match with id " + matchId + " not found"));
	}

	@PostMapping("/edit/{matchId}")
	public ResponseEntity<?> processMatchForm(@PathVariable("matchId") Long matchId,
			@Valid @RequestBody ResultRegistrationForm resultRegistrationForm) {

		Long id = resultRegistrationService.registerResults(matchId, resultRegistrationForm);
		HttpHeaders responseHeader = new HttpHeaders();
		return new ResponseEntity<>(id, responseHeader, HttpStatus.OK);
	}
	@PutMapping("/close/{matchId}")
	public void closeMatch(@PathVariable("matchId") Long matchId) {
		matchRepository.closeMatch(matchId);

	}
}
