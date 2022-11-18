package com.cognixia.jump.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Cyphers;
import com.cognixia.jump.model.CyphersType;
import com.cognixia.jump.repository.CyphersTypeRepository;

@Service
public class CyphersTypeService 
{
	@Autowired
	CyphersTypeRepository ctr;
	
	
	public List<CyphersType> getAllCypherTypes()
	{
		return ctr.findAll();
	}
	
	public List<Cyphers> getCyphersByCyphersType(String cType) throws ResourceNotFoundException
	{
		List<CyphersType> ctl = ctr.findAll();
		List<Cyphers> toReturn = null;
		for (CyphersType x : ctl)
		{
			if (x.getName().equalsIgnoreCase(cType))
			{
				toReturn = x.getCypher();
			}
		}
		if (toReturn == null)
		{
			throw new ResourceNotFoundException("Cypher Type " + cType + " does not exist");
		}
		return toReturn;
	}
}
