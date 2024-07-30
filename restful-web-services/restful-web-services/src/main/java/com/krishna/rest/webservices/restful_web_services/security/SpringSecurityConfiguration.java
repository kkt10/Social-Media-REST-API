package com.krishna.rest.webservices.restful_web_services.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		 http.authorizeHttpRequests(auth->auth.anyRequest().authenticated()); //ALL REQUESTS MUST BE AUTHENTICATION
		 
		 //FOR A REST API WE NEED TO ONLY HAVE BASIC AUTHENTICATION..
		 http.httpBasic(withDefaults());//Generates a pop up where qwe are being asked for a username and password and stuff..
		 http.csrf().disable();
		return http.build();
		
	}
	
}
