package com.marcolenzo.gameboard.api;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.marcolenzo.gameboard.api.exceptions.BadRequestException;
import com.marcolenzo.gameboard.commons.model.Gameboard;
import com.marcolenzo.gameboard.commons.model.User;
import com.marcolenzo.gameboard.commons.repositories.GameboardRepository;
import com.marcolenzo.gameboard.commons.repositories.UserRepository;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class GameboardController {

	@Autowired
	private GameboardRepository repository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/api/gameboard", method = RequestMethod.POST)
	public Gameboard createGameboard(@Valid @RequestBody Gameboard gameboard) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!gameboard.getUsers().contains(currentUser.getNickname())) {
			gameboard.getUsers().add(currentUser.getNickname());
		}
		gameboard.setId(UUID.randomUUID().toString());
		gameboard.setAdmins(Lists.newArrayList(currentUser.getNickname()));

		return repository.save(gameboard);
	}

	@RequestMapping(value = "/api/gameboard/{id}", method = RequestMethod.PUT)
	public Gameboard createGameboard(@PathVariable String id, @Valid @RequestBody Gameboard gameboard)
			throws BadRequestException {
		if (!id.equals(gameboard.getId())) {
			throw new BadRequestException("IDs cannot be updated.");
		}
		return repository.save(gameboard);
	}

	@RequestMapping(value = "/api/gameboard/{id}", method = RequestMethod.GET)
	public Gameboard getGameboardById(@PathVariable String id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "/api/gameboard", method = RequestMethod.GET, params = { "user" })
	public List<Gameboard> getGameboardByUser(@RequestParam(value = "user", required = true) String userId) {
		return repository.findByUsers(userId);
	}

	@RequestMapping(value = "/api/gameboard/test", method = RequestMethod.GET)
	public Gameboard test() {
		Gameboard gameboard = new Gameboard();
		gameboard.setId(UUID.randomUUID().toString());
		gameboard.setName("My Board");
		gameboard.setType("RESISTANCE");
		
		List<String> users = Lists.newArrayList("Marco", "Andy");
		List<String> admins = Lists.newArrayList("Marco");
		gameboard.setUsers(users);
		gameboard.setAdmins(admins);
		return gameboard;
	}

}
