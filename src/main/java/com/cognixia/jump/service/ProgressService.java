package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Cyphers;
import com.cognixia.jump.model.Progress;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.CyphersRepository;
import com.cognixia.jump.repository.ProgressRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class ProgressService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CyphersRepository cyphersRepo;
	
	@Autowired
	ProgressRepository progRepo;
	
	public List<Progress> getAllProgress(){
		
		return progRepo.findAll();
	}
	
	public boolean addProgress(Long userId, Long cypherId) throws ResourceNotFoundException{
		//create pathVariable in controller for cypherId
		Optional<User> users = userRepo.findById(userId);
		Optional<Cyphers> cyphers = cyphersRepo.findById(cypherId);

		if (users.isEmpty() && cyphers.isEmpty()) {
			throw new ResourceNotFoundException("One of user or cypher is null!");
		}
		Progress prog = new Progress();
		prog.addCyphersUsers(cyphers.get(), users.get());
		prog.setStatus("incomplete");
		progRepo.save(prog);
		return true;

	}

	public boolean completedProgress(Long id) {

		Optional<Progress> curr_prog = progRepo.findById(id);
		
		if(curr_prog.isEmpty()) {
			return false;
		}
		
		curr_prog.get().setStatus("completed");
		progRepo.save(curr_prog.get());
		
		return true;
	}
	
	public boolean inprogressProgress(Long id) {

		Optional<Progress> curr_prog = progRepo.findById(id);
		
		if(curr_prog.isEmpty()) {
			return false;
		}
		
		curr_prog.get().setStatus("in-progress");
		progRepo.save(curr_prog.get());
		
		return true;
	}

	public boolean deleteProgress(Long id) {
		Optional<Progress> curr_prog = progRepo.findById(id);
		
		if(curr_prog.isEmpty()) {
			return false;
		}
		progRepo.deleteById(id);
		return true;
	}
	
	
	
}
