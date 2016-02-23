package com.marcolenzo.gameboard.commons.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.commons.model.UserAvatar;

public interface UserAvatarRepository extends MongoRepository<UserAvatar, String> {

}
