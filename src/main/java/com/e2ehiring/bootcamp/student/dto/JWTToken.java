package com.e2ehiring.bootcamp.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JWTToken implements Serializable {
	
	private String jwtToken;
	
}