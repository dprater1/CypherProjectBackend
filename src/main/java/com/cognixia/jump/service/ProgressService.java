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

	public boolean completedProgress(Long id) throws ResourceNotFoundException{

		Optional<Progress> curr_prog = progRepo.findById(id);
		
		if(curr_prog.isEmpty()) {
			throw new ResourceNotFoundException("This cypher is not attached to this user!");
		}
		
		curr_prog.get().setStatus("completed");
		progRepo.save(curr_prog.get());
		
		return true;
	}
	
	public boolean inprogressProgress(Long id) throws ResourceNotFoundException {

		Optional<Progress> curr_prog = progRepo.findById(id);
		
		if(curr_prog.isEmpty()) {
			throw new ResourceNotFoundException("This cypher is not attached to this user!");
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
	
	public Cyphers findCypherInProgById(Long progressId) throws ResourceNotFoundException{
		Optional<Progress> curr_prog = progRepo.findById(progressId);
		
		if(curr_prog.isEmpty()) {
			throw new ResourceNotFoundException("This cypher is not attached to this user!");
		}
		Optional<Cyphers> cypher = cyphersRepo.findById(curr_prog.get().getCypher().getId());
		
		if(cypher.isEmpty()) {
			throw new ResourceNotFoundException("This cypher is not attached to this user!");
		}
		return cypher.get();
		
	}
	
	public User findUserInProgById(Long progressId) throws ResourceNotFoundException{
		Optional<Progress> curr_prog = progRepo.findById(progressId);
		
		if(curr_prog.isEmpty()) {
			throw new ResourceNotFoundException("This cypher is not attached to this user!");
		}
		Optional<User> user = userRepo.findById(curr_prog.get().getUser().getId());
		
		if(user.isEmpty()) {
			throw new ResourceNotFoundException("This cypher is not attached to this user!");
		}
		return user.get();
		
	}
	
	public List<Progress> findProgbyUserId(Long userId) throws ResourceNotFoundException{
		Optional<User> curr_user = userRepo.findById(userId);
		
		if(curr_user.isEmpty()) {
			throw new ResourceNotFoundException("This user does not exist!");
		}
		List<Progress> progList = userRepo.findProgressByUsername(curr_user.get().getUsername());
		if(progList.isEmpty()) {
			throw new ResourceNotFoundException("This user has done no cyphers!");
		}
		return progList;
	}
	
}
