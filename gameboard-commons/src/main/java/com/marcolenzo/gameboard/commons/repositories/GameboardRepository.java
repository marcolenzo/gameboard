package com.marcolenzo.gameboard.commons.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.commons.model.Gameboard;

public interface GameboardRepository extends MongoRepository<Gameboard, String> {

}
