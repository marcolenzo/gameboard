package com.marcolenzo.gameboard.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sample REST Controller.
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
@RestController
public class SampleController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "test";
	}


}
