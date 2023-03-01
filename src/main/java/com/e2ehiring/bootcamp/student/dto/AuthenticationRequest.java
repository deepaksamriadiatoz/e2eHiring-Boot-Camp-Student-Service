package com.e2ehiring.bootcamp.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

	private String userName;
	private String password;
		
}