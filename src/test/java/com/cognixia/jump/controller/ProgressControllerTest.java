package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cognixia.jump.exception.DuplicateUserException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.model.Cyphers;
import com.cognixia.jump.model.Progress;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.ProgressRepository;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.ProgressService;
import com.cognixia.jump.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProgressController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProgressControllerTest 
{
	@MockBean
	MyUserDetailsService muds;

	@MockBean
	JwtRequestFilter filter;

	@MockBean
	ProgressService ps;
	
	@MockBean
	ProgressRepository pr;
	
	@MockBean
	UserService us;
	
	@MockBean
	UserRepository ur;
	
	@InjectMocks
	ProgressController pc;
	
	@Autowired
	MockMvc mock;
	
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testGetAllProgress() throws Exception {
		
		String uri = "/api/progress/all";
		
		MvcResult mvcResult = mock.perform(
				MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testAddProgress() throws Exception{
		
		String uri = "/api/progress/add/{id}";
		Long id = 1L;
		User u = (new User(id,"John","Cena","cantSeeMe77","attitudeAdjustment","JC77@email.com",Role.ROLE_USER, true,null));
		Progress testProgress = new Progress(1L,"incomplete", u, null);


		when(ps.addProgress(Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(true);
		when(us.findByUsername(Mockito.any(String.class))).thenReturn(u);
		
		mock.perform(post(uri,id)
				//.content(asJsonString(u))
				.content(asJsonString(testProgress))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				).andDo(print())
				.andExpect(status().isCreated());

	

	}
	
	@Test
	public void testAddProgessThrowsResourceNotFoundException() throws Exception{
		
		String uri = "/api/user/signup";
		Progress testProgress = new Progress(1L,"incomplete", null, null);
		when(ps.addProgress(Mockito.any(Long.class), Mockito.any(Long.class))).thenThrow(new ResourceNotFoundException());
		
		mock.perform(post(uri,testProgress)
			.content(asJsonString(testProgress))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound());
	
		
	}
	
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testCompleteProgress() throws Exception{
		
		String uri = "/api/progress/complete/{progressId}";
		Long id = 1L;;
		Progress testProgress = new Progress(1L,"incomplete", null, null);

		when(ps.completedProgress(Mockito.any(Long.class))).thenReturn(true);
		
		mock.perform(put(uri,id)
				.content(asJsonString(testProgress))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				).andDo(print())
				.andExpect(status().isOk());
		
	

	}
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testfindCypherInProgById() throws Exception 
	{
		String uri = "/api/progress/find/{progId}";
		Long progId = 1L;
		Progress testProgress = new Progress(1L,"incomplete", null, null);
		Cyphers testCypher = new Cyphers(1L, "42", "The answer to life", "da bibl","easy",null,null);
		
		when(ps.findCypherInProgById(progId)).thenReturn(testCypher);
		
		
		mock.perform(get(uri,testProgress.getId()))
			   .andDo(print())
			   .andExpect( status().isOk());
		
	}
	
	@Test
	public void testDeleteProgress() throws Exception 
	{
		Progress testProgress = new Progress(1L,"incomplete", null, null);;
		String uri = "/api/progress/delete/{id}";
		when(ps.deleteProgress(testProgress.getId())).thenReturn(true);
		
	
		mock.perform(delete(uri,testProgress.getId()))
			   .andDo(print())
			   .andExpect( status().isOk());
		//assertEquals(200, status.isOk());
		
	}
	
	// converts any object to a JSON string
	public static String asJsonString(final Object obj) 
	{
		try 
		{
			return new ObjectMapper().writeValueAsString(obj);
		} 
		catch (JsonProcessingException e) 
		{
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	
}
