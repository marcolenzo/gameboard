package com.marcolenzo.gameboard.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findOneByEmail(String email);

	User findOneByNickname(String nickname);

}
