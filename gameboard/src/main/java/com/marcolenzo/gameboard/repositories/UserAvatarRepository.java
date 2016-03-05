package com.marcolenzo.gameboard.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.model.UserAvatar;


public interface UserAvatarRepository extends MongoRepository<UserAvatar, String> {

}
