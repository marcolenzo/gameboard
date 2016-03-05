package com.marcolenzo.gameboard.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ResistanceGameRepository gameRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable String id) {
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

	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
	public List<User> getUsers() {
		List<User> users = repository.findAll();
		for (User user : users) {
			user.setPassword(null);
		}
		return users;
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.POST)
	public User createUser(@Valid @RequestBody User user) {
		// Do not accept preset IDs.
		user.setId(null);
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User savedUser = repository.save(user);
		return savedUser;
	}

	@PreAuthorize("#id == principal.id")
	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable String id, @Valid @RequestBody User user)
			throws BadRequestException,
			ForbiddenException {

		if (!id.equals(user.getId())) {
			throw new BadRequestException("IDs cannot be updated.");
		}

		User currentUser = repository.findOne(id);
		
		// Changing email and changing password requires previous password check
		if(!currentUser.getEmail().equals(user.getEmail()) || !Strings.isNullOrEmpty(user.getPassword())) {
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
