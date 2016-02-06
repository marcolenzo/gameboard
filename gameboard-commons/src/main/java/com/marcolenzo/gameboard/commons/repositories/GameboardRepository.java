package com.marcolenzo.gameboard.commons.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.commons.model.Gameboard;

public interface GameboardRepository extends MongoRepository<Gameboard, String> {

	List<Gameboard> findByUsers(String userId);

}
