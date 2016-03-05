package com.marcolenzo.gameboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.marcolenzo.gameboard.exceptions.BadRequestException;
import com.marcolenzo.gameboard.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.model.Board;
import com.marcolenzo.gameboard.model.PlayerStatistics;
import com.marcolenzo.gameboard.model.ResistanceGame;
import com.marcolenzo.gameboard.model.User;
import com.marcolenzo.gameboard.repositories.BoardRepository;
import com.marcolenzo.gameboard.repositories.ResistanceGameRepository;
import com.marcolenzo.gameboard.repositories.UserRepository;

@Component
public class UserServices {

	@Autowired
	private UserRepository repository;

	@Autowired
	private ResistanceGameRepository gameRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User getUserById(String id) {
		if (id.equals("me")) {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = repository.findOne(user.getId());
			user.setPassword(null);
			return user;
		}
		else {
			User user = repository.findOne(id);
			user.setPassword(null);
			return user;
		}
	}

	public List<User> getUsers() {
		List<User> users = repository.findAll();
		for (User user : users) {
			user.setPassword(null);
		}
		return users;
	}

	public User createUser(User user) {
		// Do not accept preset IDs.
		user.setId(null);
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User savedUser = repository.save(user);
		return savedUser;
	}

	public User updateUser(User user, String id) throws BadRequestException, ForbiddenException {
		if (!id.equals(user.getId())) {
			throw new BadRequestException("IDs cannot be updated.");
		}

		User currentUser = repository.findOne(id);

		// Changing email and changing password requires previous password check
		if (!currentUser.getEmail().equals(user.getEmail()) || !Strings.isNullOrEmpty(user.getPassword())) {
			if (!passwordEncoder.matches(user.getPreviousPassword(), currentUser.getPassword())) {
				throw new ForbiddenException("Invalid credentials");
			}

			// TODO implement change
		}

		if (!currentUser.getNickname().equals(user.getNickname())) {
			currentUser.setNickname(user.getNickname());
			currentUser = repository.save(currentUser);

			// Update username in boards and games
			// TODO improve this by dropping nickname in board and game collections
			List<Board> boards = boardRepository.findByPlayersUserId(id);
			for (Board board : boards) {
				for (PlayerStatistics stats : board.getPlayers()) {
					if (stats.getUserId().equals(id)) {
						stats.setNickname(user.getNickname());
					}
				}
				boardRepository.save(board);
				List<ResistanceGame> games = gameRepository.findByBoardId(board.getId());
				for (ResistanceGame game : games) {
					for (PlayerStatistics player : game.getPlayerStats()) {
						if (player.getUserId().equals(id)) {
							player.setNickname(user.getNickname());
						}
					}
					gameRepository.save(game);
				}
			}
		}

		currentUser.setPassword(null);

		return currentUser;
	}
}
