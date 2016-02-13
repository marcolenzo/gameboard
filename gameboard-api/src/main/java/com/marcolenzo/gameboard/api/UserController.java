package com.marcolenzo.gameboard.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.marcolenzo.gameboard.commons.model.Board;
import com.marcolenzo.gameboard.commons.model.User;
import com.marcolenzo.gameboard.commons.repositories.BoardRepository;
import com.marcolenzo.gameboard.commons.repositories.ResistanceGameRepository;
import com.marcolenzo.gameboard.commons.repositories.UserRepository;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class UserController {

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
			user.setPassword(null);
			return user;
		}
		return null;
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
	public List<User> getUsers() {
		List<User> users = repository.findAll();
		for (User user : users) {
			user.setPassword(null);
		}
		return users;
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.GET, params = { "boardId" })
	public List<User> getUsersByBoard(@RequestParam(value = "boardId") String boardId) {
		Board board = boardRepository.findOne(boardId);
		List<User> users = Lists.newArrayList();
		for (String id : board.getUsers()) {
			User user = repository.findOne(id);
			user.setPassword(null);
			users.add(user);
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

}
