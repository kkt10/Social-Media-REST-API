package com.krishna.rest.webservices.restful_web_services.user;
//USER BEAN..
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
//WE NEED TO BUILD A REST API AROUND THIS USER BEAN, WE NEED TO SAVE DATA, RETRIVE AND DO ALL KINDS OD THINGS HERE..
//NOW ALL OF THESE DATA IS PROBABALY STORED IN SOME FORM OF A DATABASE,SO TO PERFORM CERTAIN OPERATIONS ON THE DATABASE.. WE WILL CREATE A DAO OBJECT..
///DATA ACCESS OBJECT

@Entity(name="User_Details") //To tell our database that this bean in an entity we use entity annotataion..
public class User { // since user is a keyword for our h2 database we need to rename the table to something else coz it is an error..

	protected User () {
		
	}
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min=2, message="name should have a minimum of 2 characters")
	@JsonProperty("user_name")//With this we can customize how our api responses look like as an output.. aab finally it will look like user_name
	private String name;
	
	@Past(message="Birth date should be in the past")
	@JsonProperty("DOB")
	private LocalDate birthDate;
	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Post> posts; //user has a relationship with the posts.. and it is a one to many relationship ie one user can have many posts..
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	
	
	
}
