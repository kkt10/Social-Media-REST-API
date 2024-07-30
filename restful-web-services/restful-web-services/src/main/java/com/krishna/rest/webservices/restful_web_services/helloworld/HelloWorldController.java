package com.krishna.rest.webservices.restful_web_services.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//CREATE A REST API, GIVE IT A SPEICFIC URL..

@RestController
public class HelloWorldController {

	//to pick up the right message from the messages.propetries..
	// we use something called the message source..
	
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource=messageSource;
	}
	
	
	
	@GetMapping(path="/hello-world") //WE CAN USE OUR GENERAL REQUEST MAAPING ALSO GET MAPPING IS USED SPECIFICALLY FOR GET, SO THAT WE DONT HAVE TO 
	//SPECIFY THE METHOD ANYMORE, JUST THE PATH..
	private String sayHello() {
		return "Hello, World";
	}
	
	//we can define other languages in messages.properties of those languages, like for dutch we use messages_nl.properties, then when we go to the headers in api tester and go accept language it will let us know..
	@GetMapping(path="/hello-world-languagechange")
	private String sayHelloLangChange() {
		//locale tells us about the geographical region and stuff...
		Locale locale= LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
		//return "Hello, World v2";
	}
	// we need to define certain messages that we will use, then we write the code to pick those messages up..
	
	@GetMapping(path="/hello-world-bean") 
	private HelloWorldBean sayHelloBean() {
		return new HelloWorldBean("Hello, World"); //so instead of returning a string back we are returning an instance/object of our own class..
		//so we have created a class called hello world bean, which has a member variable message, we are sending "hello world" as the value into the message, we are 
		//creating a bean and returning it back..
	} // and coz of this we will get it in a json format...
	
	//if i type the /hello-world/path-variable/Krishna, this Krishna would be mapped by spring MVC to the name in the methodhelloWorldPathVariable, toh jo bhi naam
	// we pass through the url, will be mapped to the JSON..
	
	@GetMapping(path="/hello-world/path-variable/{name}") 
	private HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean("Hello, World"+ " " + name);
	}
	
	
}
