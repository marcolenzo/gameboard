package com.marcolenzo.gameboard.model.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.marcolenzo.gameboard.model.ResistanceGame;


@Component
public class ResistanceGameValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		if (clazz.isAssignableFrom(ResistanceGame.class)) {
			return true;
		}
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ResistanceGame game = (ResistanceGame) target;
		switch (game.getPlayers().size()) {
		case 5:
		case 6:
			if (game.getSpies().size() != 2) {
				errors.reject("Invalid number of spies.");
			}
			break;
		case 7:
		case 8:
		case 9:
			if (game.getSpies().size() != 3) {
				errors.reject("Invalid number of spies.");
			}
			break;
		case 10:
			if (game.getSpies().size() != 4) {
				errors.reject("Invalid number of spies.");
			}
			break;
		default:
			errors.reject("Invalid number of players.");
		}

	}

}
