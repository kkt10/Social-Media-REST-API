package com.krishna.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
//YAHA PE SIRF CONTROL WALA PART,REQUEST MAPPING WAALA PART NOT A LOT OF LOGIC..
@RestController
public class UserRescource {

//Since the user dao has the details of the users, and the user dao service is a spring bean, as it has a component annotation, so it is managed by spring, so we will autowire it 
	//to user rescource, 
	//Auto wiring-- create a variable of that class, then autowire it using the constrcutor..
	//AUTOWIRING SE JO METHODS HAM DAOSERVICE CLASS MAIN DEFINE KIYE THE UNNMETHODS KO HAM,
	//AAB DAOSERVICE CLASS KE OBJECT KE THROUGH ACCESS KAR SAKTE HAIN, TO USE ELSEWHERE..
	private DaoService service;
	
	public UserRescource(DaoService service) {
	super();
	this.service = service;
}

	@GetMapping("/users")
	public List<User> retriveAllUsers(){
		
		return service.findAll();
	}
	
	//GET THE DETAIL OF A SPECIFIC USER...
//	@GetMapping("/users/{id}")
//	public User retreveuserbyId(@PathVariable int id){
//		 User user= service.findOne(id);
//		 if(user==null) {
//			 throw new UserNotFoundException("id:"+id);//THIS GENERATES A CUSTOM EXCEPTION THAT WE HAVE CREATED...
//		 }
//		 return user;
//		
//	}
	
	//so when we are creating a new user we must be able to pass the data which is simmilar to our 
	//stataic list so thats why User user, and we use request Body, tells that paramater should be bound to the body of the web request, in the body we will have all the details of the user..
	//and that would let us do this post mapping...
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) { //NOW THE PROBLEM WAS THAT, WE COULD ADD USERS WITH EMPTY NAMES AND BITTHDATES IN FUTURE AND ALL THOSE THINGS, WE SHOULD'NT BE ALLOWED THAT, SO WE ADD VALIDATIONS..//THE VALID ANNOTAION WHAT IT DOES IS whenever the binding happens, the validations which are defined in your object are automatically invoked.. these validation specifications are to be specified in the datatype creation in our User class..
	User savedUser=	service.saveUser(user);
	//return ResponseEntity.created(null).build(); //returns a created response status ie a 201..
		URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		//can we retrun the URI(uniform rescource identifier) of the created rescourse so that the user can directly go to the url and check only that user..to do that we do specify a location..
		//what this part of code is doing is that, from the current request /users, it is adding the path id replace the id from the id of the user whcih gets created everytime, and getting the id from the get id and attaching it to the url and converting it to a uri, so that we finally get a location, which we will 
		//return..
		
		return ResponseEntity.created(location).build();
	}
	
	//DELETE A USER
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id){
		  service.deletebyID(id);
	}
	
	//USE OF HATEOAS, SO ALONSIDE RETRIEVEING THE USER INFO WE CAN ALSO RETRUN A LINK TAKING YOU BACK TO THE USERS JSON WITH ALL USERS..
	//SINCE WE DO NOT WANT TO CHANGE EVERY CLASS TO ACCOMODATE THESE LINKINGS WE WILL USE COMETHING CALLED AS AN ENTITY MODEL..
	//WE WRAP THE USER CLASS INTO AN ENTITY MODEL
	//TO ADD LINKS TO OUR APPLIACTION WE HAVE ANOTHER CLASS WHICH IS PRESENT WHICH IS WebMvcLinkBuilder..
	@GetMapping("/users/{id}")
	public EntityModel<User> retreveuserbyId(@PathVariable int id){
		 User user= service.findOne(id);
		 if(user==null) {
			 throw new UserNotFoundException("id:"+id);//THIS GENERATES A CUSTOM EXCEPTION THAT WE HAVE CREATED...
		 }
		 
		 EntityModel<User> entityModel= EntityModel.of(user);
		 WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retriveAllUsers());
		 //WE could have hardcoded the link as well, but since if the (/users) changes then we will have to chnage the link as well, so we are instead taking the entire method only as the link..
		 //link to the method on function and then we get the class and the method of retireve all users, which is stored in the link variable..
		 entityModel.add(link.withRel("all-users"));
		 return entityModel;
		
	}
}
