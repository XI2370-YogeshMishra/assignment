package com.xebia.tennistest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xebia.tennistest.entity.League;

public interface LeagueRepository extends JpaRepository<League, Long> {

    @Query("select distinct c from League c join Match m on c.id = m.competitionId where m.id = :matchId")
    Optional<League> findByMatchId(@Param("matchId") Long matchId);
    
    @Modifying
    @Query("update League l set l.winningPlayerId=:winningPlayerId ")
    int  setWinner(@Param("winningPlayerId") long winningPlayerId );
  
    
}