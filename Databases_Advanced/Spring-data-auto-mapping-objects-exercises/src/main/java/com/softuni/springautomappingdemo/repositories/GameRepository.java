package com.softuni.springautomappingdemo.repositories;

import com.softuni.springautomappingdemo.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    boolean existsGameById(Long id);
    boolean existsGameByTitleLike(String title);

    Optional<Game> findGameById(Long id);
    Optional<Game> findGameByTitleLike(String title);
}
