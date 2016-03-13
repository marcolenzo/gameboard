package com.marcolenzo.gameboard.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marcolenzo.gameboard.model.Action;
import com.marcolenzo.gameboard.repositories.ActionRepository;

/**
 * Controller managing request dealing with user avatars management.
 * @author Marco Lenzo
 *
 */
@Controller
public class ActionController {

	@Autowired
	private ActionRepository repository;

	@RequestMapping(value = "/api/action", method = RequestMethod.GET)
	public @ResponseBody List<Action> getActions() {
		return repository.findAll();
	}

}
