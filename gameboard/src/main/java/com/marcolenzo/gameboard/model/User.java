package com.marcolenzo.gameboard.model;

import java.util.Collection;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Gameboard User.
 * @author Marco Lenzo
 *
 */
@Document
public class User implements UserDetails {

	private static final long serialVersionUID = -4735516610490938577L;

	@Id
	private String id;

	@NotEmpty
	@Email
	@Indexed(unique = true)
	private String email;

	@Size(min = 8, max = 128)
	private String password;

	@NotEmpty
	@Size(min = 1, max = 64)
	@Indexed(unique = true)
	private String nickname;

	@Transient
	@Size(min = 8, max = 128)
	private String previousPassword;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @return the previousPassword
	 */
	public String getPreviousPassword() {
		return previousPassword;
	}

	/**
	 * @param previousPassword the previousPassword to set
	 */
	public void setPreviousPassword(String previousPassword) {
		this.previousPassword = previousPassword;
	}

}
