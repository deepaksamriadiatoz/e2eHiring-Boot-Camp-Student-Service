package com.e2ehiring.bootcamp.student.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "Student")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	@Id
	private String id;

	private String name;

	private String email;

	private List<String> enrolledCourses;

	private String password;
}
