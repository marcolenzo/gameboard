package com.marcolenzo.gameboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Gameboard Application Launcher.
 * @author Marco Lenzo
 *
 */
@SpringBootApplication
@EnableScheduling
public class GameboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameboardApplication.class, args);
	}

}
