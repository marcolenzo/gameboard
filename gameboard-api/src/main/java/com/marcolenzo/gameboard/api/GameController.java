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

import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.commons.model.Board;
import com.marcolenzo.gameboard.commons.model.BoardPlayer;
import com.marcolenzo.gameboard.commons.model.ResistanceGame;
import com.marcolenzo.gameboard.commons.repositories.BoardRepository;
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

	@Autowired
	private BoardRepository boardRepository;

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
		Board board = boardRepository.findOne(game.getBoardId());
		return rateGame(game, board);
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

	private ResistanceGame rateGame(ResistanceGame game, Board board) {
		Set<BoardPlayer> resistance = Sets.newHashSet();
		Set<BoardPlayer> spies = Sets.newHashSet();
		for (String userId : game.getPlayers()) {
			if (game.getSpies().contains(userId)) {
				spies.add(board.getPlayersMap().get(userId));
			}
			else {
				resistance.add(board.getPlayersMap().get(userId));
			}
		}

		// Compute average resistance ELO
		double resistanceElo = 0;
		int count = 0;
		for (BoardPlayer user : resistance) {
			resistanceElo += user.getElo();
			count++;
		}
		resistanceElo = resistanceElo / count;

		// Compute average spies ELO
		double spiesElo = 0;
		count = 0;
		for (BoardPlayer user : spies) {
			spiesElo += user.getElo();
			count++;
		}
		spiesElo = spiesElo / count;
		
		game.setResistanceElo((int) resistanceElo);
		game.setSpiesElo((int) spiesElo);

		// Transform elos
		resistanceElo = (int) Math.pow(10, resistanceElo / 400);
		spiesElo = (int) Math.pow(10, spiesElo / 400);

		for (BoardPlayer user : resistance) {
			ratePlayer(user, game.getBoardId(), game.getResistanceWin() ? 1 : 0, spiesElo, false);
		}

		for (BoardPlayer user : spies) {
			ratePlayer(user, game.getBoardId(), game.getResistanceWin() ? 0 : 1, resistanceElo, true);
		}

		boardRepository.save(board);
		return repository.save(game);
	}
	
	private void ratePlayer(BoardPlayer user, String boardId, int score, double opponentsElo, boolean isSpy) {
		Integer elo = user.getElo();
		// Check if this is the first time we rate this user;
		if (elo == null) {
			elo = 1500;
		}
		// Transform user's elo
		double tranformedElo = (int) Math.pow(10, (double) elo / 400);
		double expectedScore = tranformedElo / (tranformedElo + opponentsElo);
		Integer finalElo = (int) (elo + (32 * (score - expectedScore)));

		user.setElo(finalElo);
		user.setMatchesPlayed(user.getMatchesPlayed() + 1);
		if (isSpy) {
			user.setMatchesPlayedAsSpy(user.getMatchesPlayedAsSpy() + 1);
			if (score == 1) {
				user.setMatchesWonAsSpy(user.getMatchesWonAsSpy() + 1);
				user.setMatchesWon(user.getMatchesWon() + 1);
			}
		}
		else {
			user.setMatchesPlayedAsResistance(user.getMatchesPlayedAsResistance() + 1);
			if (score == 1) {
				user.setMatchesWonAsResistance(user.getMatchesWonAsResistance() + 1);
				user.setMatchesWon(user.getMatchesWon() + 1);
			}
		}

	}




}
