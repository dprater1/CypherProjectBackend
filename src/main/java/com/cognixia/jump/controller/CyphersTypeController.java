package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Cyphers;
import com.cognixia.jump.model.CyphersType;

import com.cognixia.jump.service.CyphersTypeService;

@RestController
@RequestMapping("api") 
public class CyphersTypeController 
{
	@Autowired
	CyphersTypeService cts;
	
	@GetMapping("/types/all")
	public ResponseEntity<?> getAllCyphersType()
	{
		List<CyphersType> allCTs = cts.getAllCypherTypes();
		return ResponseEntity.status(200).body(allCTs);
	}

	//@GetMapping("/types/all")
	public ResponseEntity<?> getAllCypherxsType()
	{
		List<CyphersType> allCTs = cts.getAllCypherTypes();
		return ResponseEntity.status(200).body(allCTs);
	}
	
	@GetMapping("cyphers/type/{cType}")
	public List<Cyphers> getCyphersByCypherType(@PathVariable String cType) throws ResourceNotFoundException
	{
		return cts.getCyphersByCyphersType(cType);
	}
}
