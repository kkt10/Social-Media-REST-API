package com.krishna.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
//saara logic yaha pe bhai....
@Component
public class DaoService {

	private static List<User> users = new ArrayList<>();
	private static int count=0;
	//static main hamne ye define kar diya hai..
	static {
		users.add(new User(++count,"Krishna",LocalDate.now().minusYears(25)));
		users.add(new User(++count,"Tans",LocalDate.now().minusYears(24)));
		users.add(new User(++count,"Ms",LocalDate.now().minusYears(22)));
	}
	
	public List<User> findAll(){
		return users;
	}
	public User findOne(int id) {
		java.util.function.Predicate<? super User> predicate= user -> user.getId().equals(id);
//		return users.stream().filter(predicate).findFirst().get(); //one of the most important things is to return the right response back to the user, the .get method
		//when we use will return the value if it exits otherwise throws a NoSuchElementException, so instead of that we would want to return null instead..
		return users.stream().filter(predicate).findFirst().orElse(null); //This will give us like a white page with nothing and 200 response with nothing as output.. if we use the integers in url, coz its id that is 
		//being used.., otherwise its a bad request 400 coming.. we dont want this either..
		//so we go to the user rescource to users/{id} and define a custom exeption there that will be thrown if users= null..
	}
	
	public void deletebyID(int id) {
		java.util.function.Predicate<? super User> predicate= user -> user.getId().equals(id);
		users.removeIf(predicate);
	}
	
	public User saveUser(User user) {
		user.setId(++count);
		users.add(user);
		return user;
	}
	
	
	
	
}
