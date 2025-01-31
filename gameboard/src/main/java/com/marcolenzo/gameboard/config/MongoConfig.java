package com.marcolenzo.gameboard.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

/**
 * MongoDB connection configuration.
 * @author Marco Lenzo
 *
 */
@Configuration
public class MongoConfig {

	@Value("mongo.host")
	private String mongoHost;

	public @Bean MongoClient mongoClient() throws UnknownHostException {
		return new MongoClient(mongoHost);
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoClient(), "gameboard");
	}

}
