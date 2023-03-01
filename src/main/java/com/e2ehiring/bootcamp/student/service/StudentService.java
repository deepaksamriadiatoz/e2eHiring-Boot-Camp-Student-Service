package com.e2ehiring.bootcamp.student.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.e2ehiring.bootcamp.student.domain.Student;

public interface StudentService {

	
	Student save(Student student);
	
	Student updateStudent(Student student, String id);
	
	Student updateStudentPartially(Student student, String id) throws Exception;
	
	Page<Student> findAll(Pageable pageable);
	
	Student findById(String id) throws Exception;
	
	void deleteById(String id);
	
}
