package com.devs4j.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import com.devs4j.users.models.User;
import com.github.javafaker.Faker;

@Service
public class UserServiceUsingLists {

	@Autowired
	private Faker faker;

	private List<User> users = new ArrayList<>();

	@PostConstruct
	public void init() {
		for (int i = 0; i < 100; i++) {
			this.users.add(new User(faker.funnyName().name(), faker.name().username(), faker.dragonBall().character()));
		}

	}

	public List<User> getUsers(String startWith) {
		if (startWith != null) {
			return users.stream().filter(u->u.getUserName().startsWith(startWith)).collect(Collectors.toList());
		}
		return this.users;
	}

	public User getUserByUserName(String username) {
		return users.stream().filter(u -> u.getUserName().equals(username)).findAny()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found",username)));
	}
	
	public User createUser(User user) {
		if (users.stream().anyMatch(u->u.getUserName().equals(user.getUserName()))) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User % already exists", user.getUserName()));
		}
		users.add(user);
		
		return user; 
	}
	
	public User updateUser(User user, String username) {
		User userToBeUpdated = getUserByUserName(username);
		userToBeUpdated.setNickName(user.getNickName());
		userToBeUpdated.setPassword(user.getPassword());
		return userToBeUpdated;
		
	}
	
	public void deleteUser(String username) {
		User userToDelete = getUserByUserName(username);
		users.remove(userToDelete);
	}

}
