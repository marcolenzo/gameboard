package com.marcolenzo.gameboard.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.marcolenzo.gameboard.model.User;

/**
 * Mock {@link Authentication} factory.
 * @author Marco Lenzo
 *
 */
public class MockAuthenticationFactory {

	private MockAuthenticationFactory() {

	}

	public final static String USER_ID = "id";

	public final static String USER_EMAIL = "email@email.com";

	public final static String USER_NICKNAME = "MyNick";

	public final static String USER_PASSWORD = "MySecret";

	public static Authentication createMockAuthentication() {
		User user = new User();
		user.setId(USER_ID);
		user.setEmail("email@email.com");
		user.setNickname("User's Nickname");
		user.setPassword("123123123");
		return new UsernamePasswordAuthenticationToken(user, "123123123");
	}

	public static Authentication createMockAuthentication(String userId) {
		User user = new User();
		user.setId(userId);
		user.setEmail("email@email.com");
		user.setNickname("User's Nickname");
		user.setPassword("123123123");
		return new UsernamePasswordAuthenticationToken(user, "123123123");
	}
}
