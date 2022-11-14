package com.cognixia.jump.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;


//Userdeatils -? used by spring security 
@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository ur; 

	
	/*
	 * method is goinf to be called by spring security when a new http request comes in
	 * steps
	 * 1. extract the username passed in the request
	 * 2. call the method below and find the user associated with the request
	 * 3. check if the password in  the db matches the password in the request
	 * 4. then check if enable, their role allows them access this endpoint, if credentials expired, etc
	 * 5. as long as all of that passes, the request can be fulfilled
	 */
	@Override
	public UserDetails loadUserByUsername(String Username) throws UsernameNotFoundException
	{
		Optional<User> user = ur.findByUsername(Username);
		
		if(user.isEmpty())
		{
			throw new UsernameNotFoundException("User with username " + Username + " not found");
		}
		return new MyUserDetails(user.get());
	}
	
}
