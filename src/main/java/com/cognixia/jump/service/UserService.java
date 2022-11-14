package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	public List<User> getAllUsers(){
		
		return userRepo.findAll();
	}
	
	public boolean createUser(User user) {
		
		if(user != null) {
			user.setId(null);
			//each password for a new user will get encoded
			user.setPassword(encoder.encode(user.getPassword()));
			userRepo.save(user);
			return true;
		}
		
		return false;
	}
	
	public boolean deleteUser(Long id) {
		
		Optional<User> found = userRepo.findById(id);
		
		if(found.isEmpty()) {
			return false;
		}
		userRepo.deleteById(id);
		return true;
		
	}
	
}
