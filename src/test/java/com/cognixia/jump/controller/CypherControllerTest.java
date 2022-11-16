package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.model.Cyphers;
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
	@WithMockUser
	void testGetAllCyphers() throws Exception {
		String uri = "/cyphers/all";
		List<Cyphers> cypher = new ArrayList<Cyphers>();
		
		cypher.add(new Cyphers(1L, "answer", "ceaser","hints","easy",null, null));
		when(service.getAllCyphers()).thenReturn(cypher);
		
		mockMvc.perform( get(uri) )
		.andDo( print() )
		.andExpect( status().isOk() )
		.andExpect( jsonPath("$.length()").value( cypher.size() ) )
		.andExpect( jsonPath("$[0].id").value( cypher.get(0).getId()) )
		.andExpect( jsonPath("$[0].answer").value( cypher.get(0).getAnswer() ) )
		;
	}
	
	@Test
	void testCreateProduct() {
		fail("Not yet implemented");
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	void testgetCypherById() throws Exception {
		String uri = "/cyphers/cyphers/{id}";
		String answer="ay caramba";
		Long id = 1L;
		List<Cyphers> cypher = new ArrayList<Cyphers>();
		
		cypher.add(new Cyphers(id, answer, "ceaser","hints","easy",null, null));
		ResponseEntity<Cyphers> re = ResponseEntity.status(200).body(cypher.get(0));
		when( service.getCypherById(id)).thenReturn(re);
		
		mockMvc.perform( get(uri,id ) )
		.andDo( print() )
		.andExpect( status().isOk() )
		.andExpect( jsonPath("id").value( cypher.get(0).getId() ) )
		.andExpect( jsonPath("answer").value( cypher.get(0).getAnswer() ) )
		;
		verify( service, times(1) ).getCypherById(id);
		verifyNoMoreInteractions(service);
	}

	@Test
	void testUpdateProduct() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCypherEasy() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCypherMedium() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCypherHard() {
		fail("Not yet implemented");
	}


}
