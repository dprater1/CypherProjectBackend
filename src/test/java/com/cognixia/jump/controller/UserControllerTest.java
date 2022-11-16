package com.cognixia.jump.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest 
{
	@MockBean
	MyUserDetailsService muds;

	@MockBean
	JwtRequestFilter filter;

	@MockBean
	UserService us;
	
	@MockBean
	UserRepository ur;
	
	@InjectMocks
	UserController uc;
	
	@Autowired
	MockMvc mock;
	
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testGetAllUsers() throws Exception {
		
		String uri = "/api/all";
		
		MvcResult mvcResult = mock.perform(
				MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
	}
	
	@Test
	//@WithMockUser(roles = "ADMIN")
	public void createUserTest() throws Exception{
		
		String uri = "/api/user/signup";
		User testUser = new User(1L,"John","Cena","cantSeeMe77","attitudeAdjustment","JC77@email.com",Role.ROLE_USER, true,null);
		
		when(us.createUser(Mockito.any(User.class))).thenReturn(true);
		
		mock.perform(post(uri)
				.content(asJsonString(testUser))
				.contentType(MediaType.APPLICATION_JSON_VALUE)//.accept(MediaType.APPLICATION_JSON))
				).andDo(print())
				.andExpect(status().isCreated());
	
		//verify(us, times(1) ).createUser(testUser);
		//verifyNoMoreInteractions(us);
	}
	
	@Test
	public void createUserTestThrowsDuplicateUserException() throws Exception{
		
		String uri = "/api/user/signup";
		User testUser = new User(0L,"John","Cena","cantSeeMe77","attitudeAdjustment","JC77@email.com",Role.ROLE_USER, true,null);
		when(us.createUser(Mockito.any(User.class))).thenThrow(new DuplicateUserException("Username: " + testUser.getUsername() + "is already in use"));
		//User testUser2= new User(0L,"John","Cena","cantSeeMe77","attitudeAdjustment","JC77@email.com",Role.ROLE_USER, true,null);
		//when(us.createUser(Mockito.any(User.class))).thenReturn(false);
		
		mock.perform(post(uri,testUser)
			.content(asJsonString(testUser))
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest());
	
		
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testGetUserByUserName() throws Exception 
	{

		String uri = "/api/all";
		
		MvcResult mvcResult = mock.perform(
				MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
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
