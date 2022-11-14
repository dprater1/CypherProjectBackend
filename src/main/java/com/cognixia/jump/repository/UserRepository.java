package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Progress;
import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	@Query("Select u.progress from User u where u.username = ?1")
	List<Progress> findProgressByUsername(String username);
	
	List<Progress> findUserByProgress(String status);
}
