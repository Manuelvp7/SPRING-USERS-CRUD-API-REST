package com.devs4j.users.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devs4j.users.entities.User;
import com.devs4j.users.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private	UserService service;
	
	@GetMapping
	public ResponseEntity<Page<User>> getUsers(@RequestParam(required=false, value = "page", defaultValue = "0")int page, @RequestParam(required = false, value =  "size", defaultValue = "10")int size){
		return new ResponseEntity<>(service.getUsers(page, size), HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId")Integer userId) {
		return new ResponseEntity<User>(service.getUserById(userId), HttpStatus.OK);
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username")String username){
		return new ResponseEntity<User>(service.getUserByUserName(username), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<User> authenticate(@RequestBody User user){
		return new ResponseEntity<User>(service.getUserByUserNameAndPassword(user.getUsername(), user.getPassword()), HttpStatus.OK);
	}
	
}
