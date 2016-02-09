package com.marcolenzo.gameboard.commons.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.commons.model.Game;

public interface GameRepository extends MongoRepository<Game, String> {

	List<Game> findByBoardId(String boardId);

}
