package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Cyphers;
import com.cognixia.jump.model.Progress;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.ProgressService;
import com.cognixia.jump.service.UserService;


@RestController
@RequestMapping("/api")
public class ProgressController {
	
	@Autowired
	ProgressService progService;
	
	@Autowired
	UserService userService;

	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@GetMapping("/progress/all")
	public ResponseEntity<?> getAllProgress(){
		List<Progress> progress = progService.getAllProgress();
		
		return new ResponseEntity<>(progress, HttpStatus.OK);
		
	}
	
	@PostMapping("/progress/add/{cypherId}")
	public ResponseEntity<?> addProgress(@PathVariable Long cypherId) throws ResourceNotFoundException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		
		if(!authentication.isAuthenticated()) {
			throw new ResourceNotFoundException("This user is not found!");
		}
		User user = userService.findByUsername(authentication.getName());
		progService.addProgress(user.getId(), cypherId);
		
		return new ResponseEntity<>(user, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/progress/complete/{cypherId}")
	public ResponseEntity<?> completeProgress(@PathVariable Long progressId) throws ResourceNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		
		if(!authentication.isAuthenticated()) {
			throw new ResourceNotFoundException("This user is not found!");
		}
		User user = userService.findByUsername(authentication.getName());
		progService.completedProgress(progressId);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
		
		
	}
	
	@DeleteMapping("/progress/delete/{id}")
	public ResponseEntity<?> deleteProgress(@PathVariable Long id){
		
		if(progService.deleteProgress(id)) {
		return new ResponseEntity<>("Deleted progress from list ", HttpStatus.OK);
		}
		
		return new ResponseEntity<>("ID not found!", HttpStatus.NOT_FOUND);
	}
	@GetMapping("/progress/find/{progId}")
	public ResponseEntity<?> findCypherInProgById(@PathVariable Long progId) throws ResourceNotFoundException{
		Cyphers cypher = progService.findCypherInProgById(progId);
		
		return new ResponseEntity<>(cypher, HttpStatus.OK);
		
		
	}
}
