package com.marcolenzo.gameboard.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.api.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
