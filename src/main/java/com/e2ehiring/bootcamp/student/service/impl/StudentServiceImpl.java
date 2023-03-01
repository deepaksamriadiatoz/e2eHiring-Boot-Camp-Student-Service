package com.e2ehiring.bootcamp.student.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.e2ehiring.bootcamp.student.constants.StudentServiceConstants;
import com.e2ehiring.bootcamp.student.domain.Student;
import com.e2ehiring.bootcamp.student.dto.Course;
import com.e2ehiring.bootcamp.student.proxy.InstructorServiceProxy;
import com.e2ehiring.bootcamp.student.repo.StudentRepo;
import com.e2ehiring.bootcamp.student.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepo studentRepo;

	@Autowired
	private InstructorServiceProxy instructorServiceProxy;

	@Autowired
	private MongoTemplate mongoTemplate;

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
		if (!studentOptional.isPresent()) {
			throw new Exception("Student does not exist with provided id " + id);
		}

		Student existingStudent = studentOptional.get();
		if (!ObjectUtils.isEmpty(student.getEmail())) {
			existingStudent.setEmail(student.getEmail());
		}

		if (!ObjectUtils.isEmpty(student.getEnrolledCourses())) {
			existingStudent.setEnrolledCourses(student.getEnrolledCourses());
		}

		if (!ObjectUtils.isEmpty(student.getName())) {
			existingStudent.setName(student.getName());
		}

		if (!ObjectUtils.isEmpty(student.getPassword())) {
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
		if (!studentOptional.isPresent()) {
			throw new Exception("Student doesn't exist with provided id " + id);
		}

		return studentOptional.get();
	}

	@Override
	public void deleteById(String id) {
		studentRepo.deleteById(id);
	}

	@Override
	public Page<Student> searchStudentByName(Pageable pageable, String name) {
		Query query = new Query().with(pageable);
		Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
		query.addCriteria(Criteria.where(StudentServiceConstants.SEARCH_BY_NAME).regex(pattern));
		List<Student> students = mongoTemplate.find(query, Student.class);
		Page<Student> studentPage = PageableExecutionUtils.getPage(students, pageable,
				() -> mongoTemplate.count(query, Student.class));
		return studentPage;
	}

	@Override
	public List<Course> getStudentCourses(String id) throws Exception {
		Optional<Student> studentOptional = studentRepo.findById(id);

		if (!studentOptional.isPresent()) {
			throw new Exception("Student doesn't exist with provided id " + id);
		}

		List<String> enrolledCourseIds = studentOptional.get().getEnrolledCourses();

		try {
			ResponseEntity<List<Course>> instructorServiceResponse = instructorServiceProxy.getCoursesByIds(null,
					enrolledCourseIds);

			if (instructorServiceResponse.getStatusCode().is2xxSuccessful()) {
				return instructorServiceResponse.getBody();
			}

		} catch (Exception e) {
			log.error("Logging error while calling instructor service with reqeust :{}", enrolledCourseIds);
			throw e;
		}

		return null;
	}

	@Override
	public void enrollStudentInCourses(String studentId, List<String> courseIds) throws Exception {
		Optional<Student> studentOptional = studentRepo.findById(studentId);
		if (!studentOptional.isPresent()) {
			throw new Exception("Student doesn't exist with provided id " + studentId);
		}

		Student student = studentOptional.get();
		student.setEnrolledCourses(courseIds);
		studentRepo.save(student);
	}

	@Override
	public List<Student> getStudentByIds(List<String> ids) {
		Query query = new Query();
		query.addCriteria(Criteria.where(StudentServiceConstants.STUDENT_ID).all(ids));
		return mongoTemplate.find(query, Student.class);
	}

}
