package com.marcolenzo.gameboard.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.model.Board;


public interface BoardRepository extends MongoRepository<Board, String> {

	List<Board> findByPlayersUserId(String userId);

}
