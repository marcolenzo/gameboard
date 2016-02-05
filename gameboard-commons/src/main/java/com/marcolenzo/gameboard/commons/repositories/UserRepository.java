package com.marcolenzo.gameboard.commons.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.commons.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findOneByEmail(String email);

}
