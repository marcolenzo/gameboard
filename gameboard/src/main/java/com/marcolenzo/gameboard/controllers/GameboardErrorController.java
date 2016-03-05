package com.marcolenzo.gameboard.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller which maps error requests.
 * @author Marco Lenzo
 *
 */
@Controller
public class GameboardErrorController implements ErrorController {

	@RequestMapping(value = "/error")
	public String error() {
		return "index";
	}

	@Override
	public String getErrorPath() {
		return "index";
	}

}
