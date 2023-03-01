package com.e2ehiring.bootcamp.student.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.e2ehiring.bootcamp.student.config.JwtTokenUtil;
import com.e2ehiring.bootcamp.student.config.MyUserDetail;
import com.e2ehiring.bootcamp.student.config.MyUserDetailsService;
import com.e2ehiring.bootcamp.student.dto.AuthenticationRequest;
import com.e2ehiring.bootcamp.student.dto.JWTToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginResource {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().body("Bad credentials : " + e.getMessage());
		}
		final MyUserDetail myUserDetails = (MyUserDetail) myUserDetailsService
				.loadUserByUsername(authenticationRequest.getUserName());
		final String jwt = jwtTokenUtil.generateToken(myUserDetails);

		log.info("User authenticated");
		return ResponseEntity.ok(new JWTToken(jwt));
	}

}