package com.e2ehiring.bootcamp.student.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.e2ehiring.bootcamp.student.domain.Student;

public interface StudentRepo extends MongoRepository<Student, String> {

	public Student findByName(String name);
}
