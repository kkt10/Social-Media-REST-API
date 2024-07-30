package com.krishna.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.krishna.rest.webservices.restful_web_services.jpa.PostRepository;
import com.krishna.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;


//SO THE POINT HERE IS WE NEED OUR RESCOURCE TO TALK TO OUR DATABASES AND RETRIEVE VALUES FROM THAT DATABASE, and
//Perform all the other operations using the repository interface instead of the user interface..
@RestController
public class UserJpaRescource {


	
	private UserRepository userRespository;
	
	private PostRepository postRepository;
	
	
	
	public UserJpaRescource(UserRepository userRespository, PostRepository postRepository) {
	super();
	this.userRespository=userRespository;
	this.postRepository=postRepository;
}

	@GetMapping("/jpa/users")
	public List<User> retriveAllUsers(){
		
		return userRespository.findAll();
	}
	

	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) { //NOW THE PROBLEM WAS THAT, WE COULD ADD USERS WITH EMPTY NAMES AND BITTHDATES IN FUTURE AND ALL THOSE THINGS, WE SHOULD'NT BE ALLOWED THAT, SO WE ADD VALIDATIONS..//THE VALID ANNOTAION WHAT IT DOES IS whenever the binding happens, the validations which are defined in your object are automatically invoked.. these validation specifications are to be specified in the datatype creation in our User class..
	User savedUser=	userRespository.save(user);
	//return ResponseEntity.created(null).build(); //returns a created response status ie a 201..
		URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

		
		return ResponseEntity.created(location).build();
	}
		
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){
		userRespository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts") //the logic to retrieve all posts for a specific user..using the id..
	public List<Post> RetrievePostsForUser(@PathVariable int id){
		
		 Optional<User> user= userRespository.findById(id);
		 if(user.isEmpty()) {
			 throw new UserNotFoundException("id:"+id);
		 }
		 return user.get().getPosts();
	}
	
	

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retreveuserbyId(@PathVariable int id){
		 Optional<User> user= userRespository.findById(id);
		 if(user.isEmpty()) {
			 throw new UserNotFoundException("id:"+id);//THIS GENERATES A CUSTOM EXCEPTION THAT WE HAVE CREATED...
		 }
		 
		 EntityModel<User> entityModel= EntityModel.of(user.get());
		 WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retriveAllUsers());
		 entityModel.add(link.withRel("all-users"));
		 return entityModel;
		
	}
	@PostMapping("/jpa/users/{id}/posts") //the logic to Create a post for a specific user..using the url... and using all the stuff we have already used before...
	public ResponseEntity<Object> CreatePostsForUser(@PathVariable int id,@Valid @RequestBody Post post){
		
		 Optional<User> user = userRespository.findById(id);
		 if(user.isEmpty()) {
			 throw new UserNotFoundException("id:"+id);
		 }
		 post.setUser(user.get());
		 Post savedPost= postRepository.save(post);
		 URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();

			
			return ResponseEntity.created(location).build();
		 
	}
}
