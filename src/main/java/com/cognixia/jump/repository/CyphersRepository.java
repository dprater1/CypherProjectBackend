package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Cyphers;

@Repository
public interface CyphersRepository extends JpaRepository<Cyphers, Long> {

	boolean existsByQuestion(String question);

	@Query("SELECT u FROM Cyphers u WHERE u.difficulty=easy")
	public List<Cyphers> findEasyCypher();
	
	@Query("SELECT u FROM Cyphers u WHERE u.difficulty=medium")
	public List<Cyphers> findMediumCypher();
	
	@Query("SELECT u FROM Cyphers u WHERE u.difficulty=hard")
	public List<Cyphers> findHardCypher();
	//
}
