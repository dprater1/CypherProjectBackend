package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers(){
		List<User> users = userService.getAllUsers();
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PostMapping("/user/signup")
	public ResponseEntity<?> createUser(@RequestBody @Valid User user){
		
		if(userService.createUser(user)) {
			User created = user;
			return ResponseEntity.status(201).body(created);
		}
		
		return new ResponseEntity<>("Failed to create user: " + user, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id){
		if(userService.deleteUser(id)) {
			return new ResponseEntity<>("Deleted user with id: " + id + " from list ", HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Failed to delete user.", HttpStatus.NOT_ACCEPTABLE);
		
	}
	
}
