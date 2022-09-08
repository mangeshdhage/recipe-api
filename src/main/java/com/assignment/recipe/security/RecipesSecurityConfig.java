package com.assignment.recipe.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.assignment.recipe.util.AppConstants;

@EnableWebSecurity
public class RecipesSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String[] SWAGGER_WHITELIST = { "/v2/api-docs", "/v3/api-docs", "/**/v3/api-docs",
			"/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security",
			"/swagger-ui.html", "**/swagger-ui.html", "/**/swagger-ui.html**", "/swagger-ui.html**", "/webjars/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, SWAGGER_WHITELIST).permitAll().anyRequest()
				.authenticated().and().httpBasic().and().csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(AppConstants.USER_NAME)
				.password(passwordEncoder().encode(AppConstants.PASSWORD)).authorities(AppConstants.ADMIN_ROLE);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
