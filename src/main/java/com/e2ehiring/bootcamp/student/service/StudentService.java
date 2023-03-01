package com.e2ehiring.bootcamp.student.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.e2ehiring.bootcamp.student.domain.Student;
import com.e2ehiring.bootcamp.student.dto.Course;

public interface StudentService {

	Student save(Student student);

	Student updateStudent(Student student, String id);

	Student updateStudentPartially(Student student, String id) throws Exception;

	Page<Student> findAll(Pageable pageable);

	Student findById(String id) throws Exception;

	void deleteById(String id);

	Page<Student> searchStudentByName(Pageable pageable, String name);

	List<Course> getStudentCourses(String id) throws Exception;

	void enrollStudentInCourses(String studentId, List<String> courseIds) throws Exception;

	List<Student> getStudentByIds(List<String> ids);

}
