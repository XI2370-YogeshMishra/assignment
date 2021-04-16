package com.xebia.tennistest.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.xebia.tennistest.entity.Match;
import com.xebia.tennistest.util.BracketPosition;


public interface MatchRepository extends JpaRepository<Match, Long> {

    Collection<Match> findByPlayer1IdOrPlayer2Id(@Param("id1") Long player1Id, @Param("id2") Long player2Id);

    Optional<Match> findByCompetitionIdAndBracketPosition(@Param("id") Long competitionId, @Param("bracketPosition") BracketPosition bracketPosition);

 
}
