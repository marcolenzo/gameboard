package com.marcolenzo.gameboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.marcolenzo.gameboard.repositories.UserRepository;



/**
 * {@link UserDetailsService} that relies on a {@link UserRepository} which is
 * an extension of a {@link MongoRepository}.
 * 
 * @author Marco Lenzo <lenzo.marco@gmail.com>
 *
 */
public class MongoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findOneByEmail(username);
	}

}
