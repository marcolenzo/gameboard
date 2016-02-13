package com.marcolenzo.gameboard.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.commons.model.ResistanceGame;
import com.marcolenzo.gameboard.commons.model.User;
import com.marcolenzo.gameboard.commons.repositories.ResistanceGameRepository;
import com.marcolenzo.gameboard.commons.repositories.UserRepository;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private ResistanceGameRepository repository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/api/game", method = RequestMethod.GET, params = { "boardId" })
	public List<ResistanceGame> getGamesByBoardId(@RequestParam(value = "boardId", required = true) String boardId) {
		return repository.findByBoardId(boardId);
	}

	@RequestMapping(value = "/api/game/{id}", method = RequestMethod.GET)
	public ResistanceGame getGame(@PathVariable String id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "/api/game", method = RequestMethod.POST)
	public ResistanceGame createGame(@Valid @RequestBody ResistanceGame game) {
		LOGGER.info("Creating new game for board {}", game.getBoardId());
		game = repository.save(game);
		// TODO Use SI to trigger rating.
		game = rateGame(game);
		// TODO dirty code. clean!
		return repository.save(game);
	}

	@RequestMapping(value = "/api/game/test", method = RequestMethod.GET)
	public ResistanceGame getGameTest() {
		ResistanceGame game = new ResistanceGame();
		game.setId(UUID.randomUUID().toString());
		game.setBoardId(UUID.randomUUID().toString());
		game.setStartTime(LocalDateTime.now());
		game.setPlayers(Sets.newHashSet("123", "abc", "xxx", "yyy", "zzz"));
		game.setSpies(Sets.newHashSet("123", "abc"));
		game.setResistanceWin(true);
		return game;
	}

	private ResistanceGame rateGame(ResistanceGame game) {
		Set<User> resistance = Sets.newHashSet();
		Set<User> spies = Sets.newHashSet();
		for (String userId : game.getPlayers()) {
			if (game.getSpies().contains(userId)) {
				spies.add(userRepository.findOne(userId));
			}
			else {
				resistance.add(userRepository.findOne(userId));
			}
		}

		// Compute average resistance ELO
		double resistanceElo = 0;
		int count = 0;
		for (User user : resistance) {
			if (user.getEloRatings() == null) {
				user.setEloRatings(Maps.newHashMap());
			}
			Integer elo = user.getEloRatings().get(game.getBoardId());
			if (elo != null) {
				resistanceElo += elo;
			}
			else {
				resistanceElo += 1500;
			}
			count++;
		}
		resistanceElo = resistanceElo / count;

		// Compute average spies ELO
		double spiesElo = 0;
		count = 0;
		for (User user : spies) {
			Integer elo = user.getEloRatings().get(game.getBoardId());
			if (elo != null) {
				spiesElo += elo;
			}
			else {
				spiesElo += 1500;
			}
			count++;
		}
		spiesElo = spiesElo / count;
		
		game.setResistanceElo((int) resistanceElo);
		game.setSpiesElo((int) spiesElo);

		// Transform elos
		resistanceElo = (int) Math.pow(10, resistanceElo / 400);
		spiesElo = (int) Math.pow(10, spiesElo / 400);

		for(User user : resistance) {
			rateUser(user, game.getBoardId(), game.getResistanceWin() ? 1 : 0, spiesElo);
		}

		for (User user : spies) {
			rateUser(user, game.getBoardId(), game.getResistanceWin() ? 0 : 1, resistanceElo);
		}

		return game;

	}
	
	private void rateUser(User user, String boardId, int score, double opponentsElo) {
		Integer elo = user.getEloRatings().get(boardId);
		// Check if this is the first time we rate this user;
		if (elo == null) {
			elo = 1500;
		}
		// Transform user's elo
		double tranformedElo = (int) Math.pow(10, (double) elo / 400);
		double expectedScore = tranformedElo / (tranformedElo + opponentsElo);
		Integer finalElo = (int) (elo + (32 * (score - expectedScore)));

		user.getEloRatings().put(boardId, finalElo);
		userRepository.save(user);
	}




}
