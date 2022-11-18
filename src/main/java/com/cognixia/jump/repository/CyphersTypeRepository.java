package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Cyphers;
import com.cognixia.jump.model.CyphersType;

@Repository
public interface CyphersTypeRepository extends JpaRepository<CyphersType, Long> {

	
	//public List<Cyphers> findCyphersByCypherType(String cType);
}
