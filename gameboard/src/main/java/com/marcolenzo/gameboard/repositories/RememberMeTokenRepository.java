package com.marcolenzo.gameboard.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcolenzo.gameboard.model.RememberMeToken;


public interface RememberMeTokenRepository extends MongoRepository<RememberMeToken, String> {

	RememberMeToken findOneBySeries(String seriesId);

	List<RememberMeToken> removeByUsername(String username);

}
