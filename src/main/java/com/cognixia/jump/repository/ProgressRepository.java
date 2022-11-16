package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Progress;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {

//	List<Progress> findUserByProgressByStatus(User user);
//	//value goes into below
//	boolean findProgressByStatus(String status);
	
	
	
}
