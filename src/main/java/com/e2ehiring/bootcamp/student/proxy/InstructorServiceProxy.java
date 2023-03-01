package com.e2ehiring.bootcamp.student.proxy;

import java.net.URI;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.e2ehiring.bootcamp.student.config.FeignConfiguration;
import com.e2ehiring.bootcamp.student.dto.Course;

@FeignClient(name = "data", url = "www.e2ehiring.com", configuration = FeignConfiguration.class)
public interface InstructorServiceProxy {
	
	@GetMapping("/course")
    ResponseEntity<List<Course>> getCoursesByIds(URI uri, List<String> idList);
	
}