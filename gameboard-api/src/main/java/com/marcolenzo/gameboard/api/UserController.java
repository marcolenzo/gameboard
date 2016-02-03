package com.marcolenzo.gameboard.api;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcolenzo.gameboard.api.model.User;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class UserController {

	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable String id) {
		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setEmail("bla@bla.com");
		user.setNickname("Nickname");
		user.setPassword("Passwd");
		return user;
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.POST)
	public String getUser(@Valid @RequestBody User user) {
		// TODO persist user.
		return user.getId();
	}


}
