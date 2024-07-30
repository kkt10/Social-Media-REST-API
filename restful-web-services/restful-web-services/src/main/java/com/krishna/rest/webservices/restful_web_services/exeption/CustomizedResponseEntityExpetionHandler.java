package com.krishna.rest.webservices.restful_web_services.exeption;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.krishna.rest.webservices.restful_web_services.user.UserNotFoundException;

//we are here trying to generate our own custom response, so we are overriding the Response entity exception handler and creating our own expetion handler, so that we can use it for our custom defined expceptions..\
@ControllerAdvice
public class CustomizedResponseEntityExpetionHandler extends ResponseEntityExceptionHandler{

	//making use of our own custom exception structure and we are returning it back as a response...
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleallException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	//NOW ON OUR VALIDATIONS IF THEY ARE NOT VALID POST REQUESTS, THEY RETURN A 400, WHICH SHOULD NOT BE THE CASE, IT SHOULD INSTEAD TELL US THE EXCEPTION SO AGAIN WE WILL CREATE OUR CUSTOM EXCEPTION AND 
	//AND OVERRIDING THE METHOD IN THE RESPONSE ENTITY EXCEPTION HANDLER..
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity (errorDetails, HttpStatus.BAD_REQUEST);
		//You can do  lot of things here, with the exception object ex, and you print the custom message, print one of the messages and much more according to the user.. 
	}
}
