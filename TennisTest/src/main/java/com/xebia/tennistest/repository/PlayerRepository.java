package com.xebia.tennistest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xebia.tennistest.entity.Player;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByFirstNameAndLastName(@Param("fn") String firstName, @Param("ln") String lastName);

    List<Player> findByIdIn(List<Long> ids);

    @Query("select distinct p from Player p join Match m on p.id = m.player1Id or p.id = m.player2Id where m.id = :matchId")
    List<Player> findAllByMatchId(@Param("matchId")Long matchId);

    @Query("select distinct p from Player p join Match m on p.id = m.player1Id or p.id = m.player2Id where m.competitionId = :competitionId")
    List<Player> findAllByCompetitionId(@Param("competitionId")Long competitionId);
}
