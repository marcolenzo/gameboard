package com.marcolenzo.gameboard.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcolenzo.gameboard.exceptions.BadRequestException;
import com.marcolenzo.gameboard.exceptions.ForbiddenException;
import com.marcolenzo.gameboard.model.User;
import com.marcolenzo.gameboard.services.UserServices;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserServices userServices;

	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable String id) {
		return userServices.getUserById(id);
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
	public List<User> getUsers() {
		return userServices.getUsers();
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.POST)
	public User createUser(@Valid @RequestBody User user) {
		return userServices.createUser(user);
	}

	@PreAuthorize("#id == principal.id")
	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable String id, @Valid @RequestBody User user)
			throws BadRequestException,
			ForbiddenException {
		return userServices.updateUser(user, id);
	}

}
