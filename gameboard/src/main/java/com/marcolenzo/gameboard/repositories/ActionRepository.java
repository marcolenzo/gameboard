package com.marcolenzo.gameboard.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.model.Action;


public interface ActionRepository extends MongoRepository<Action, String> {

}
