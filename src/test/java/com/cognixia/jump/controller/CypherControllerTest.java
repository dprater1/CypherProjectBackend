package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.repository.CyphersRepository;
import com.cognixia.jump.service.CyphersService;
import com.cognixia.jump.service.MyUserDetailsService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CypherController.class)
@AutoConfigureMockMvc(addFilters = false)
class CypherControllerTest {

	@MockBean
	CyphersService service;
	@MockBean
	CyphersRepository repo;
	@Autowired
	MockMvc mockMvc;
	@MockBean
	MyUserDetailsService service2;
	@MockBean
	JwtRequestFilter filter;
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
