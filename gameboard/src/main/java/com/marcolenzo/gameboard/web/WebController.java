package com.marcolenzo.gameboard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	@RequestMapping(value = "login")
	public String index() {
		return "login";
	}

}
