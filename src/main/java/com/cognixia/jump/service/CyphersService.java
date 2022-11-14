package com.cognixia.jump.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.DuplicateCypherException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Cyphers;
import com.cognixia.jump.repository.CyphersRepository;

@Service
public class CyphersService {

	@Autowired
	CyphersRepository repo;
	
	//check duplicate value.
	public boolean checkDuplicateQuestion(String question) {
		return repo.existsByQuestion(question);
		
	}
	
	//create
	public ResponseEntity<?> createCyphers(Cyphers cypher) throws DuplicateCypherException {
		if (checkDuplicateQuestion(cypher.getQuestion()) != false) {
			throw new DuplicateCypherException("you are trying to register duplicate item in the system.");
		} else {
			cypher.setId(null);

			Cyphers create = repo.save(cypher);

			return ResponseEntity.status(201).body(create);
		}
	}
	//read all
	public List<Cyphers> getAllCyphers(){
		return repo.findAll();
	}
	
	//read cypher by id
	public ResponseEntity<Cyphers> getCypherById(Long id) throws ResourceNotFoundException {
		Optional<Cyphers> found = repo.findById(id);

		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Cypher with id = " + id + " was not found");
		}

		return ResponseEntity.status(200).body(found.get());
	}
	
	
	
	//update helper methods
	public Cyphers save(Cyphers cypher) {
		return repo.save(cypher);

	}
	public Cyphers get(Long id) {
		return repo.findById(id).get();

	}
	
	//update
	public ResponseEntity<?> updateProduct(Cyphers cypher, Long id) throws ResourceNotFoundException {
		Optional<Cyphers> productOptional = repo.findById(id);
		if (productOptional.isPresent()) {
			Cyphers update = get(id);
			save(cypher);
			return ResponseEntity.status(201).body(update);
		} else
			throw new ResourceNotFoundException("Product id does not exist.");
	}
	
	
	//delete
	public boolean deleteCypherById(Long id) {

		Optional<Cyphers> found = repo.findById(id);

		if (!found.isEmpty()) {
			repo.delete(found.get());
			return true;
		}

		return false;

	}

	//custom query finding cypher by difficulty.
	public List<Cyphers> getEasyCypher() {
		return repo.findEasyCypher();
	}

	public List<Cyphers> getMediumCypher() {
		return repo.findMediumCypher();
	}

	public List<Cyphers> getHardCypher() {
		return repo.findHardCypher();
	}
}
