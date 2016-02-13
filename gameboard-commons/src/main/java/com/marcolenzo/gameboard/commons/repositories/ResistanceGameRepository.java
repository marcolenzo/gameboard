package com.marcolenzo.gameboard.commons.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.commons.model.ResistanceGame;

public interface ResistanceGameRepository extends MongoRepository<ResistanceGame, String> {

	List<ResistanceGame> findByBoardId(String boardId);

}
