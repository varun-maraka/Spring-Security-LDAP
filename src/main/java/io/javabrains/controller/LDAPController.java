package io.javabrains.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LDAPController {
	@RequestMapping("/hi")
	public String sayHi(){
		return "Hello";
	}
	
	@RequestMapping("/")
	public String allPath(){
		return "Hello, Welcome all";
	}
	
	@RequestMapping("/auth")
	public String allAuth(){
		return "Hello, Welcome Authenticated user";
	}
	
	@RequestMapping("/user")
	public String replyUser(){
		return "Hello, Welcome User";
	}
	
	@RequestMapping("/admin")
	public String replyAdmin(){
		return "Hello, Welcome Admin";
	}
}
