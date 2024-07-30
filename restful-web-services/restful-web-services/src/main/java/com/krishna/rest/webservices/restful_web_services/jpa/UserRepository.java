package com.krishna.rest.webservices.restful_web_services.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna.rest.webservices.restful_web_services.user.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	
}
