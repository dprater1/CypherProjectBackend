package com.cognixia.jump.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtRequestFilter;
import com.cognixia.jump.service.MyUserDetailsService;



@Configuration
public class SecurityConfiguration 
{
	//when you perform dependency injection, check if any of files in this app are UserDetailServiceFilkes
	//if so, create an object of that class for this variable
	@Autowired
	UserDetailsService uds;
	
	@Autowired
	MyUserDetailsService muds;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	
	private static final String[] AUTH_DOCUMENTATION = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            
    };
	
	private static final String[] AUTH_ALL = {
            "/api/user/signup",
            "/api/authenticate",
            "/api/cyphers/**"
    };
	
	private static final String[] AUTH_USER = {
			"api/deleteUser/**"

    };
	
	private static final String[] AUTH_ADMIN = {
			"/api/all",
			"/api/user"
    };

	//Authentication
	@Bean
	protected UserDetailsService userDetailsService() //uds = UserDetailService
	{
		return uds;
	}

	//Authorization
	@Bean
	protected SecurityFilterChain filterChain( HttpSecurity http) throws Exception
	{
		http.csrf().disable()
				   .authorizeRequests()
				   .antMatchers(AUTH_DOCUMENTATION).permitAll()
				   .antMatchers(AUTH_ALL).permitAll()
				   .antMatchers(AUTH_USER).hasRole("USER")
				   .antMatchers(AUTH_ADMIN).hasRole("ADMIN")
				   .anyRequest()
				   .authenticated()  //this chunck makes it so some login is needed to access any APIS
				   .and()
				   .sessionManagement()
				   .sessionCreationPolicy( SessionCreationPolicy.STATELESS ); // tell spring security to NOT create sessions
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	protected PasswordEncoder encoder()
	{
		//return NoOpPasswordEncoder.getInstance(); //just a temp solution for now that provides a plain text (unecrpted password) for learning purposes
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); 
		
		authProvider.setUserDetailsService(uds);
		authProvider.setPasswordEncoder( encoder() );
		
		return authProvider;
	}
	
	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}
