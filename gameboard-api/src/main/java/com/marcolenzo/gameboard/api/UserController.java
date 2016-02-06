package com.marcolenzo.gameboard.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.marcolenzo.gameboard.commons.model.User;
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

	@RequestMapping(value = "/api/user", method = RequestMethod.GET, params = { "nicknameOnly" })
	public List<String> getNicknames() {
		List<User> users = repository.findAll();
		List<String> nicknames = Lists.newArrayList();
		for (User user : users) {
			nicknames.add(user.getNickname());
		}
		return nicknames;
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.POST)
	public User createUser(@Valid @RequestBody User user) {
		user.setId(UUID.randomUUID().toString());
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		User savedUser = repository.save(user);
		return savedUser;
	}

}
