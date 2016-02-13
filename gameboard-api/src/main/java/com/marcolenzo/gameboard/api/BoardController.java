package com.marcolenzo.gameboard.api;

import java.util.List;
import java.util.Set;
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

import com.google.common.collect.Sets;
import com.marcolenzo.gameboard.api.exceptions.BadRequestException;
import com.marcolenzo.gameboard.commons.model.Board;
import com.marcolenzo.gameboard.commons.model.User;
import com.marcolenzo.gameboard.commons.repositories.BoardRepository;
import com.marcolenzo.gameboard.commons.repositories.UserRepository;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class BoardController {

	@Autowired
	private BoardRepository repository;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/api/board", method = RequestMethod.POST)
	public Board createGameboard(@Valid @RequestBody Board gameboard) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!gameboard.getUsers().contains(currentUser.getId())) {
			gameboard.getUsers().add(currentUser.getId());
		}
		gameboard.setId(null);
		gameboard.setAdmins(Sets.newHashSet(currentUser.getId()));

		return repository.save(gameboard);
	}

	@RequestMapping(value = "/api/board/{id}", method = RequestMethod.PUT)
	public Board createGameboard(@PathVariable String id, @Valid @RequestBody Board gameboard)
			throws BadRequestException {
		if (!id.equals(gameboard.getId())) {
			throw new BadRequestException("IDs cannot be updated.");
		}
		return repository.save(gameboard);
	}

	@RequestMapping(value = "/api/board/{id}", method = RequestMethod.GET)
	public Board getGameboardById(@PathVariable String id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "/api/board", method = RequestMethod.GET, params = { "user" })
	public List<Board> getGameboardByUser(@RequestParam(value = "user", required = true) String userId) {
		return repository.findByUsers(userId);
	}

	@RequestMapping(value = "/api/board/test", method = RequestMethod.GET)
	public Board test() {
		Board gameboard = new Board();
		gameboard.setId(UUID.randomUUID().toString());
		gameboard.setName("My Board");
		gameboard.setType("RESISTANCE");
		
		Set<String> users = Sets.newHashSet("Marco", "Andy");
		Set<String> admins = Sets.newHashSet("Marco");
		gameboard.setUsers(users);
		gameboard.setAdmins(admins);
		return gameboard;
	}

}
