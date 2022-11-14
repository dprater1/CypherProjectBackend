package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> createProduct(@RequestBody Cyphers cypher) throws DuplicateCypherException {
		return service.createCyphers(cypher);
	}
	
	//get cypher by id number
	@GetMapping("/cypers/{id}")
	public ResponseEntity<?> getCypherById(@PathVariable Long id) throws ResourceNotFoundException {

		return service.getCypherById(id);
	}
	
	//update cypher by id
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody Cyphers cypher, @PathVariable Long id)
			throws ResourceNotFoundException {
		return service.updateProduct(cypher, id);
	}
	
	// custom query
	@GetMapping("/cypher/easy")
	public List<Cyphers> getCypherEasy() {
		return service.getEasyCypher();
	}
	@GetMapping("/cypher/medium")
	public List<Cyphers> getCypherMedium() {
		return service.getMediumCypher();
	}
	@GetMapping("/cypher/hard")
	public List<Cyphers> getCypherHard() {
		return service.getHardCypher();
	}
	
}
