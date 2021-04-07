package com.mancala.game.repository;

import com.mancala.game.Model.Pit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitRepository extends JpaRepository<Pit,Long> {
}
