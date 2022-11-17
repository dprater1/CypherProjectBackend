package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.DuplicateCypherException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Cyphers;
import com.cognixia.jump.service.CyphersService;

@RestController
@RequestMapping("/cyphers")
public class CypherController {
	
	@Autowired
	CyphersService service;

	//get all cypher list
	@GetMapping("/all")
	public List<Cyphers> getAllCypers(){
		return service.getAllCyphers();
	}
	//create new cypher
	@PostMapping("/new")
	public ResponseEntity<?> createCypher(@RequestBody Cyphers cypher) throws DuplicateCypherException {
		return service.createCyphers(cypher);
	}
	
	//get cypher by id number
	@GetMapping("/cyphers/{id}")
	public ResponseEntity<?> getCypherById(@PathVariable Long id) throws ResourceNotFoundException {

		return service.getCypherById(id);
	}
	
	//update cypher by id
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCypher(@RequestBody Cyphers cypher, @PathVariable Long id)
			throws ResourceNotFoundException {
		return service.updateCypher(cypher, id);
	}
	
	//delete cypher by id
	@DeleteMapping("/delete/{id}")
	public boolean deleteCypher(@PathVariable Long id) {

		return service.deleteCypherById(id);
	}
	
	
	// custom query
	@GetMapping("/cyphers/easy")
	public List<Cyphers> getCypherEasy() {
		return service.getEasyCypher();
	}
	@GetMapping("/cyphers/medium")
	public List<Cyphers> getCypherMedium() {
		return service.getMediumCypher();
	}
	@GetMapping("/cyphers/hard")
	public List<Cyphers> getCypherHard() {
		return service.getHardCypher();
	}
	
}
