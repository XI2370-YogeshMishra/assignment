package com.xebia.tennistest.repository;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xebia.tennistest.entity.Match;
import com.xebia.tennistest.util.BracketPosition;

public interface MatchRepository extends JpaRepository<Match, Long> {

	Collection<Match> findByPlayer1IdOrPlayer2Id(@Param("id1") Long player1Id, @Param("id2") Long player2Id);

	Optional<Match> findByCompetitionIdAndBracketPosition(@Param("id") Long competitionId,
			@Param("bracketPosition") BracketPosition bracketPosition);
	@Transactional
	@Modifying
	@Query("update Match m set m.matchclosestatus=1 WHERE m.id=:matchId ")
	int closeMatch(@Param("matchId") long matchId);

}
