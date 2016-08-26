package com.pptv.dataoper.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/bcp")
public class HelloWorld {
	
	@RequestMapping("/home")
	public String home() {
		return "hello";
	}
	
	public static void main(String[] args) {
	    SpringApplication.run(HelloWorld.class, args);
	}
}
