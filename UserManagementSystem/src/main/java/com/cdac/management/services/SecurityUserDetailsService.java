package com.cdac.management.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.cdac.management.entity.User;
import com.cdac.management.repositories.UserRepository;
import com.cdac.management.security.SecurityUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.getByUserEmail(email);
		
		if(user != null) {
			return new SecurityUserDetails(user);
		}
		
		throw new UsernameNotFoundException("Could not find user with email : "+email);
		
	}

}