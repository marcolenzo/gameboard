package com.marcolenzo.gameboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.marcolenzo.gameboard.repositories.MongoPersistentTokenRepository;
import com.marcolenzo.gameboard.security.MongoUserDetailsService;

/**
 * Spring Security configuration.
 * @author Marco Lenzo
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String KEY = "c445dfa348f851909c96806770036ac83710228be5137dd35b416d5f010b9f57";

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl("/index");
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

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		return new MongoPersistentTokenRepository();
	}

	@Bean
	public RememberMeServices rememberMeServices() {
		return new PersistentTokenBasedRememberMeServices(KEY,
				userDetailsService(), persistentTokenRepository());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
		.antMatchers("/signup-success.html", "/signup-failed.html", "/signup", "/api/user/", "/resources/**", "/app/**").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").loginProcessingUrl("/login").successHandler(successHandler()).permitAll()
		.and().logout().permitAll()
		.and().rememberMe().rememberMeServices(rememberMeServices()).key(KEY)
		.and().csrf().disable();
		// @formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.GET, "/avatar/**", "/fonts/**", "/images/**", "/js/**", "/css/**");
		super.configure(web);
	}


}