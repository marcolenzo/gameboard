package com.marcolenzo.gameboard;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Gameboard Application Launcher.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@SpringBootApplication
public class GameboardApplication {

	@Value("mongo.host")
	private String mongoHost;

	public static void main(String[] args) {
		SpringApplication.run(GameboardApplication.class, args);
	}

	public @Bean Mongo mongo() throws UnknownHostException {
		return new MongoClient(mongoHost);
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), "gameboard");
	}
}
