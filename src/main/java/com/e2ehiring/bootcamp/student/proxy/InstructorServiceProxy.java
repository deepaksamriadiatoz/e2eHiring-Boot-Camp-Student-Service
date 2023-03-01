package com.e2ehiring.bootcamp.student.proxy;

import org.springframework.cloud.openfeign.FeignClient;

import com.e2ehiring.bootcamp.student.config.FeignConfiguration;

@FeignClient(name = "data", url = "www.e2ehiring.com", configuration = FeignConfiguration.class)
public interface InstructorServiceProxy {
	
	
}