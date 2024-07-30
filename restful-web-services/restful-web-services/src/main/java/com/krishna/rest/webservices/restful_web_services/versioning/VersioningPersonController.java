package com.krishna.rest.webservices.restful_web_services.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

	//THIS IS URI OR URL VERSIONING YEAH??
	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Krish Tiwari");
	}
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Krish","Tiwari from V2"));
	}
	
	//Now A CONSUMER CAN SPECIFY A VERSION AS A REQUEST PARAMTER..
	//url--  http://localhost:8080/person?version=1
	@GetMapping(path="person", params= "version=1") // so if there's a request param with version 1 only then that method will be called
	public PersonV1 getFirstVersionOfPersonRequestParamter() {
		return new PersonV1("Krish Tiwari from Requests");
	}
	
	@GetMapping(path= "person", params="version=2")
	public PersonV2 getSecondVersionOfPersonRequestParamter() {
		return new PersonV2(new Name("Krish","Tiwari from V2"));
	}
	
	//Custom Headers  Versioning can also be done.. here the consumer requests the api headers from the provider.
	@GetMapping(path="/person/header", headers= "X-API-VERSION=1") 
	public PersonV1 getFirstVersionOfPersonRequestHeader() {
		return new PersonV1("Krish Tiwari from Headers");
	}
	@GetMapping(path= "/person/header", headers="X-API-VERSION=2")
	public PersonV2 getSecondVersionOfPersonRequestHeader() {
		return new PersonV2(new Name("Krish","Tiwari from V2 headers"));
	}
	
	//MEDIA TYPE VERSIONING.. SAME WAY ALMOST.. we use a produces instead of headers or params...
	@GetMapping(path="/person/accept", produces= "application/vnd.company.app-v1+json") 
	public PersonV1 getFirstVersionOfPersonRequestMediaAcceptHeader() {
		return new PersonV1("Krish Tiwari from media");
	}
	@GetMapping(path= "/person/accept", produces="application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionOfPersonMediaAcceptHeader() {
		return new PersonV2(new Name("Krish","Tiwari from V2 Media"));
	}
}
