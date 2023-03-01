package com.e2ehiring.bootcamp.student.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.e2ehiring.bootcamp.student.domain.Student;
import com.e2ehiring.bootcamp.student.repo.StudentRepo;
import com.e2ehiring.bootcamp.student.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepo studentRepo;
	

	@Override
	public Student save(Student student) {
		student.setId(UUID.randomUUID().toString());
		return studentRepo.save(student);
	}

	@Override
	public Student updateStudent(Student student, String id) {
		student.setId(id);
		return studentRepo.save(student);
	}

	@Override
	public Student updateStudentPartially(Student student, String id) throws Exception {
		Optional<Student> studentOptional = studentRepo.findById(id);
		if(!studentOptional.isPresent()) {
			throw new Exception("Student does not exist with provided id "+id);
		}
		
		Student existingStudent = studentOptional.get();
		if(!ObjectUtils.isEmpty(student.getEmail())) {
			existingStudent.setEmail(student.getEmail());
		}
		
		if(!ObjectUtils.isEmpty(student.getEnrolledCourses())) {
			existingStudent.setEnrolledCourses(student.getEnrolledCourses());
		}
		
		if(!ObjectUtils.isEmpty(student.getName())) {
			existingStudent.setName(student.getName());
		}
		
		if(!ObjectUtils.isEmpty(student.getPassword())) {
			existingStudent.setPassword(student.getPassword());
		}
		
		return studentRepo.save(existingStudent);
		
	}

	@Override
	public Page<Student> findAll(Pageable pageable) {
		return studentRepo.findAll(pageable);
	}

	@Override
	public Student findById(String id) throws Exception {
		Optional<Student> studentOptional = studentRepo.findById(id);
		if(!studentOptional.isPresent()) {
			throw new Exception("Student doesn't exist with provided id "+id);
		}
		
		return studentOptional.get();
	}

	@Override
	public void deleteById(String id) {
		studentRepo.deleteById(id);		
	}

}
