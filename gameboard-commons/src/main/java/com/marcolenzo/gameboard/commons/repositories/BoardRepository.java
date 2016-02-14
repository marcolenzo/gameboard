package com.marcolenzo.gameboard.commons.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.commons.model.Board;

public interface BoardRepository extends MongoRepository<Board, String> {

	List<Board> findByPlayersUserId(String userId);

}
