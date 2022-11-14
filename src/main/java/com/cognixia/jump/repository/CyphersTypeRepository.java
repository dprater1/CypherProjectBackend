package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.CyphersType;

@Repository
public interface CyphersTypeRepository extends JpaRepository<CyphersType, Long> {

	
	
}
