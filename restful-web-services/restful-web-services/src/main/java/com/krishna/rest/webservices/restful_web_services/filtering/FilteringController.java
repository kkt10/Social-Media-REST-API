package com.krishna.rest.webservices.restful_web_services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

//	@GetMapping("/filtering")
//	public SomeBean filtering() {
//		return new SomeBean("value1", "value2", "value3");
//	}
	
//	@GetMapping("/filtering-list") //Even if we have a list with elements and their values, simce value2 is ignored/hidden we will still not have that list
//	//mentioned at all in our response..
//	public List<SomeBean> filteringList() {
//		return Arrays.asList(new SomeBean("value1","value2","value3"), new SomeBean("value4","value5","vlaue6"));
//	}
	
	//WE DID STATIC MAPPING USING OUR JSONIGNORE AND ALL, BUT NOW WE NEED DIFFERENT FIRLDS TO SHOW FOR DIFFERENT URL'S LIKE FILTERING LIST MAIN I WANT FIELD 1 AND 3 AND AND FILTERING MAIN I WANT 2 AND 3..
	// WE USE SOMETHING CALLED MappingJacksonValue
	//THis mapping jackson value allows me to set dynamic filters and all...
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		MappingJacksonValue mappingJacksonValue= new MappingJacksonValue(someBean);
		SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
		
	}
	
	@GetMapping("/filtering-list") 
	public MappingJacksonValue filteringList() {
		
		List<SomeBean> someBean = Arrays.asList(new SomeBean("value1","value2","value3"), new SomeBean("value4","value5","vlaue6"));
		MappingJacksonValue mappingJacksonValue= new MappingJacksonValue(someBean);
		SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
}
