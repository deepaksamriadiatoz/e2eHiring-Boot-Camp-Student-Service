package com.e2ehiring.bootcamp.student.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.e2ehiring.bootcamp.student.domain.Student;
import com.e2ehiring.bootcamp.student.repo.StudentRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	
	@Autowired
	private StudentRepo studentRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		Student user = studentRepo.findByName(userName);
		if(user==null) {
			throw new UsernameNotFoundException(userName);
		}
		
		MyUserDetail myUserDetail = new MyUserDetail(user);
		return myUserDetail;
		
	}

}