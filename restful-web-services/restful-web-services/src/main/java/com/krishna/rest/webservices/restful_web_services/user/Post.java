package com.krishna.rest.webservices.restful_web_services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Post {

	Post(){
		
	}
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min= 10)
	private String description;
	
	
	@ManyToOne(fetch = FetchType.LAZY)// we do not want our user details to be fetched along with our posts, that would be the default eager fetch type..
	@JsonIgnore //we dont want posts to be part of our response for the user and vice versa..
	private User user;

	public Post(Integer id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}
	
}
