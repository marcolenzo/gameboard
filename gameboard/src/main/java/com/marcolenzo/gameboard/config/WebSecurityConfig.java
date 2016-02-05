package com.marcolenzo.gameboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.marcolenzo.gameboard.commons.security.MongoUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl("/dashboard");
		return successHandler;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new MongoUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
		.antMatchers("/signup-success.html", "/signup-failed.html", "/signup", "/api/user/", 
						"/resources/**", "/app/**", "/css/**", "/fonts/**", "/images/**", "/js/**").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").loginProcessingUrl("/login").successHandler(successHandler()).permitAll()
		.and().logout().permitAll().and().csrf().disable();
		// @formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

}