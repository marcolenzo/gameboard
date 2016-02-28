package com.marcolenzo.gameboard.commons.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.marcolenzo.gameboard.commons.model.RememberMeToken;
import com.marcolenzo.gameboard.commons.repositories.RememberMeTokenRepository;

/**
 * A persistent remember-me token repository leveraging MongoDB.
 * @author Marco Lenzo
 *
 */
public class MongoPersistentTokenRepository implements PersistentTokenRepository {

	@Autowired
	private RememberMeTokenRepository repository;

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		repository.save(new RememberMeToken(token));
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String series) {
		RememberMeToken r = repository.findOneBySeries(series);
		if (r == null) {
			return null;
		}
		else {
			PersistentRememberMeToken token = new PersistentRememberMeToken(r.getUsername(), r.getSeries(),
					r.getTokenValue(), r.getDate());
			return token;
		}

	}

	@Override
	public void removeUserTokens(String username) {
		repository.removeByUsername(username);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		RememberMeToken token = repository.findOne(series);
		token.setDate(lastUsed);
		token.setTokenValue(tokenValue);
		repository.save(token);
	}

}
