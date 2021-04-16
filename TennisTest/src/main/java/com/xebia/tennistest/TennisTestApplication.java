package com.xebia.tennistest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.xebia.tennistest.entity.LeagueForm;
import com.xebia.tennistest.entity.Player;
import com.xebia.tennistest.repository.PlayerRepository;
import com.xebia.tennistest.service.LeagueService;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TennisTestApplication implements CommandLineRunner {

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private LeagueService leagueService;

	public static void main(String[] args) {
		SpringApplication.run(TennisTestApplication.class, args);
	}
	 @Bean
	   public Docket productApi() {
	      return new Docket(DocumentationType.SWAGGER_2).select()
	         .apis(RequestHandlerSelectors.basePackage("com.xebia.tennistest")).build();
	   }
	@Override
	public void run(String... args) throws Exception {

		playerRepository.save(new Player("MS", "Dhoni"));
		playerRepository.save(new Player("Virat", "Kohli"));
		playerRepository.save(new Player("Dinesh", "Sharma"));
		playerRepository.save(new Player("Rohit", "Sharma"));
		playerRepository.save(new Player("Vijay", "Shankar"));
		playerRepository.save(new Player("Shikhar", "Dhawan"));
		playerRepository.save(new Player("Bumrah", "Singh"));
		playerRepository.save(new Player("Yuvraj", "Singh"));
		playerRepository.save(new Player("KL", "Rahul"));
		playerRepository.save(new Player("Sanju", "Samson"));
		playerRepository.save(new Player("Manish", "Pandey"));
		playerRepository.save(new Player("Rishabh", "Pant"));
		playerRepository.save(new Player("Saurabh", "Ganguly"));
		playerRepository.save(new Player("Sachine", "Kumar"));
		playerRepository.save(new Player("Suresh", "Raina"));
		playerRepository.save(new Player("Bhuvi", "Kumar"));

		List<Long> playerIdList = new ArrayList<>();
		playerIdList.add(
				playerRepository.findByFirstNameAndLastName("MS", "Dhoni").orElseThrow(InternalError::new).getId());
		playerIdList.add(
				playerRepository.findByFirstNameAndLastName("Virat", "Kohli").orElseThrow(InternalError::new).getId());

		LeagueForm leagueForm = new LeagueForm();
		leagueForm.setName("League");
		leagueForm.setPlayerIds(playerIdList);
		leagueForm.setNumberOfWinsRequired(3);
		leagueForm.setNumberOfPointsToWin(11);

		leagueService.createCompetition(leagueForm);

	}

}
