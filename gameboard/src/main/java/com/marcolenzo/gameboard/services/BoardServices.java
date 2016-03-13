/**
 * 
 */
package com.marcolenzo.gameboard.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.annotations.ActionLoggable;
import com.marcolenzo.gameboard.exceptions.BadRequestException;
import com.marcolenzo.gameboard.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.model.Board;
import com.marcolenzo.gameboard.model.BoardStatistics;
import com.marcolenzo.gameboard.model.PlayerStatistics;
import com.marcolenzo.gameboard.model.ResistanceGame;
import com.marcolenzo.gameboard.model.User;
import com.marcolenzo.gameboard.model.comparators.PlayerStatisticsComparator;
import com.marcolenzo.gameboard.model.comparators.ResistanceGameComparator;
import com.marcolenzo.gameboard.repositories.BoardRepository;
import com.marcolenzo.gameboard.repositories.ResistanceGameRepository;

/**
 * Services for the management of boards.
 * @author Marco Lenzo
 */
@Component
public class BoardServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardServices.class);

	@Autowired
	protected BoardRepository boardRepository;

	@Autowired
	protected ResistanceGameRepository gameRepository;

	/**
	 * Creates a board.
	 * @param board
	 * @return
	 */
	public Board createBoard(Board board) {

		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// Do not accept externally defined IDs.
		board.setId(null);

		// Sanitize player set and make sure current user is present
		boolean isCurrentUserPresent = false;
		for (PlayerStatistics player : board.getPlayers()) {
			if (player.getUserId().equals(currentUser.getId())) {
				isCurrentUserPresent = true;
			}
			player.resetStatistics();
		}

		if (!isCurrentUserPresent) {
			PlayerStatistics player = new PlayerStatistics();
			player.setUserId(currentUser.getId());
			player.setNickname(currentUser.getNickname());
			board.getPlayers().add(player);
		}

		board.setAdmins(Sets.newHashSet(currentUser.getId()));

		return boardRepository.save(board);
	}
	
	/**
	 * Let current user join the board.
	 * @param boardId
	 * @return
	 * @throws BadRequestException
	 */
	public Board joinBoard(String boardId) throws BadRequestException {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Board board = boardRepository.findOne(boardId);
		if(board == null) {
			throw new BadRequestException("Invalid board ID");
		}
		
		// Verify if player has already joined the board
		for(PlayerStatistics player: board.getPlayers()) {
			if(player.getUserId().equals(currentUser.getId())) {
				return board;
			}
		}
		
		PlayerStatistics player = new PlayerStatistics();
		player.setUserId(currentUser.getId());
		player.setNickname(currentUser.getNickname());
		
		board.getPlayers().add(player);
		
		board = boardRepository.save(board);
		
		return board;
	}

	/**
	 * Updates a board.
	 * @param updatedBoard
	 * @return
	 * @throws BadRequestException
	 * @throws ForbiddenException
	 */
	@ActionLoggable
	public Board updateBoard(Board updatedBoard, String id) throws BadRequestException, ForbiddenException {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!id.equals(updatedBoard.getId())) {
			throw new BadRequestException("IDs cannot be updated.");
		}

		Board board = boardRepository.findOne(updatedBoard.getId());
		if (!board.getAdmins().contains(currentUser.getId())) {
			throw new ForbiddenException("Only board admins can perform this action");
		}

		// Make sure a owner is never removed from admins
		for (String owner : board.getOwners()) {
			updatedBoard.getAdmins().add(owner);
		}

		// Override owners. Do not allow modifications
		updatedBoard.setOwners(board.getOwners());

		return boardRepository.save(updatedBoard);
	}

	/**
	 * Get board by ID.
	 * @param id
	 * @return
	 */
	public Board getBoardById(String id) {
		return boardRepository.findOne(id);
	}

	/**
	 * Get boards where player with specified ID is present.
	 * @param playerId
	 * @return
	 */
	public List<Board> getBoardsByPlayerId(String playerId) {
		return boardRepository.findByPlayersUserId(playerId);
	}

	/**
	 * Reset board statistics.
	 * @param id
	 * @return
	 * @throws ForbiddenException
	 */
	@ActionLoggable
	public Board resetBoard(String id) throws ForbiddenException {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Board board = boardRepository.findOne(id);
		if (!board.getAdmins().contains(currentUser.getId())) {
			throw new ForbiddenException("You need to be a board admin to perform this action.");
		}

		for (PlayerStatistics player : board.getPlayers()) {
			player.resetStatistics();
		}

		board.setBoardStatistics(new BoardStatistics());

		// Intermediate save
		board = boardRepository.save(board);

		List<ResistanceGame> games = gameRepository.findByBoardId(board.getId());
		Collections.sort(games, new ResistanceGameComparator());

		for (ResistanceGame game : games) {
			this.rateGame(game, board);
		}

		return board;
	}

	public ResistanceGame rateGame(ResistanceGame game, Board board) {

		resetEloVariations(board);

		Set<PlayerStatistics> resistance = Sets.newHashSet();
		Set<PlayerStatistics> spies = Sets.newHashSet();
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
		for (PlayerStatistics user : resistance) {
			resistanceElo += user.getElo();
			count++;
		}
		resistanceElo = resistanceElo / count;

		// Compute average spies ELO
		double spiesElo = 0;
		count = 0;
		for (PlayerStatistics user : spies) {
			spiesElo += user.getElo();
			count++;
		}
		spiesElo = spiesElo / count;

		game.setResistanceElo((int) resistanceElo);
		game.setSpiesElo((int) spiesElo);

		// Transform elos
		resistanceElo = (int) Math.pow(10, resistanceElo / 400);
		spiesElo = (int) Math.pow(10, spiesElo / 400);

		for (PlayerStatistics user : resistance) {
			ratePlayer(user, game.getBoardId(), game.getResistanceWin() ? 1 : 0, spiesElo, false);
		}

		for (PlayerStatistics user : spies) {
			ratePlayer(user, game.getBoardId(), game.getResistanceWin() ? 0 : 1, resistanceElo, true);
		}

		Collections.sort(board.getPlayers(), new PlayerStatisticsComparator());

		// Set positions game
		setPositions(board.getPlayers());
		board.setBoardStatistics(updateBoardStatistics(board, game));

		board = boardRepository.save(board);

		// Save player statics in game as well to have leaderboard history
		List<PlayerStatistics> gamePlayerStatistics = Lists.newArrayList();
		for (PlayerStatistics playerStatistics : board.getPlayers()) {
			if (playerStatistics.getMatchesPlayed() > 0) {
				gamePlayerStatistics.add(playerStatistics);
			}
		}
		game.setPlayerStats(gamePlayerStatistics);

		return gameRepository.save(game);
	}

	/**
	 * 
	 * @param user
	 * @param boardId
	 * @param score
	 * @param opponentsElo
	 * @param isSpy
	 */
	private void ratePlayer(PlayerStatistics user, String boardId, int score, double opponentsElo, boolean isSpy) {
		Integer elo = user.getElo();
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

		user.setEloVariation(finalElo - elo);
	}

	private void resetEloVariations(Board board) {
		for (PlayerStatistics stat : board.getPlayers()) {
			stat.setEloVariation(0);
		}
	}

	private void setPositions(List<PlayerStatistics> playerStat) {
		// Set positions and store in game
		int rating = 0;
		int rank = 1;
		for (PlayerStatistics player : playerStat) {
			int initialPos = player.getPosition();
			if (player.getElo() == rating) {
				player.setPosition(rank - 1);
			}
			else {
				player.setPosition(rank);
				rank++;
				rating = player.getElo();
			}

			// Set position variation.
			if (initialPos == 0) {
				player.setPositionVariation(0);
			}
			else {
				player.setPositionVariation(initialPos - player.getPosition());
			}

		}
	}

	private BoardStatistics updateBoardStatistics(Board board, ResistanceGame game) {
		BoardStatistics stats = board.getBoardStatistics();
		if (stats == null) {
			stats = new BoardStatistics();
		}
		stats.setMatchesPlayed(stats.getMatchesPlayed() + 1);
		if (game.getResistanceWin()) {
			stats.setMatchesWonByResistance(stats.getMatchesWonByResistance() + 1);
		}
		switch (game.getPlayers().size()) {
		case 5:
			stats.setMatchesPlayed3v2(stats.getMatchesPlayed3v2() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance3v2(stats.getMatchesWonByResistance3v2() + 1);
			}
			break;
		case 6:
			stats.setMatchesPlayed4v2(stats.getMatchesPlayed4v2() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance4v2(stats.getMatchesWonByResistance4v2() + 1);
			}
			break;
		case 7:
			stats.setMatchesPlayed4v3(stats.getMatchesPlayed4v3() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance4v3(stats.getMatchesWonByResistance4v3() + 1);
			}
			break;
		case 8:
			stats.setMatchesPlayed5v3(stats.getMatchesPlayed5v3() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance5v3(stats.getMatchesWonByResistance5v3() + 1);
			}
			break;
		case 9:
			stats.setMatchesPlayed6v3(stats.getMatchesPlayed6v3() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance6v3(stats.getMatchesWonByResistance6v3() + 1);
			}
			break;
		case 10:
			stats.setMatchesPlayed6v4(stats.getMatchesPlayed6v4() + 1);
			if (game.getResistanceWin()) {
				stats.setMatchesWonByResistance6v4(stats.getMatchesWonByResistance6v4() + 1);
			}
			break;
		default:
			LOGGER.warn("Unexpected number of players");
		}
		return stats;
	}
	
	//TODO Export this as a custom spring security expression
	public void isCurrentUserAdminCheck(String boardId) throws ForbiddenException {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Board board = boardRepository.findOne(boardId);
		if (!board.getAdmins().contains(currentUser.getId())) {
			throw new ForbiddenException("You need to be a board admin to perform this action.");
		}
	}

}
