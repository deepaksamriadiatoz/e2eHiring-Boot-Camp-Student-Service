package com.e2ehiring.bootcamp.student.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.e2ehiring.bootcamp.student.domain.Student;
import com.e2ehiring.bootcamp.student.dto.Course;
import com.e2ehiring.bootcamp.student.service.StudentService;

import io.github.jhipster.web.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class StudentResource {

	
	@Autowired
	private StudentService studentService;
	
	@PostMapping("/student")
	public ResponseEntity<?> createStudent(@RequestBody Student student){
		log.debug("REST request to save Student : {}", student);
		try {
			Student savedStudent = studentService.save(student);
			return ResponseEntity.ok(savedStudent);
		} catch (Exception e) {
			log.error("Logging error while creating student :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
		
	}
	
	
	@PutMapping("/student/{id}")
	public ResponseEntity<?> updateStudent(@RequestBody Student student, @PathVariable("id") String id){
		log.debug("REST request to update Student : {}, id :{}", student, id);
		
		try {
			Student savedStudent = studentService.updateStudent(student, id);
			return ResponseEntity.ok(savedStudent);
		} catch (Exception e) {
			log.error("Logging error while updating student :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}	

	}
	
	@PatchMapping("/student/{id}")
	public ResponseEntity<?> updateStudentPartially(@RequestBody Student student, @PathVariable("id") String id){
		log.debug("REST request to update Student partailly : {}, id :{}", student, id);
		try {
			Student savedStudent = studentService.updateStudentPartially(student, id);
			return ResponseEntity.ok(savedStudent);
		} catch (Exception e) {
			log.error("Logging error while updating student partially student :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}	
		
	}
	
	
	@GetMapping("/students")
	public ResponseEntity<?> findAllStudent(Pageable pageable){
		log.debug("REST request to get all Students");
		try {
			Page<Student> studentPage = studentService.findAll(pageable);
			
			HttpHeaders headers = PaginationUtil
					.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), studentPage);
			return ResponseEntity.ok().headers(headers).body(studentPage.getContent());
		} catch (Exception e) {
			log.error("Logging error while finding all students :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}	
	}		
	
	@GetMapping("/student/{id}")
	public ResponseEntity<?> findStudentById(@PathVariable("id") String id){
		log.debug("REST request to find Student by id :{}", id);
		try {
			Student student = studentService.findById(id);
			return ResponseEntity.ok(student);
		} catch (Exception e) {
			log.error("Logging error while finding student by id :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	
	@DeleteMapping("/student/{id}")
	public ResponseEntity<?> deleteStudentById(@PathVariable("id") String id){
		log.debug("REST request to delete Student by id :{}", id);
		try {
			studentService.deleteById(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Logging error while deleting student by id :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@GetMapping("/student/search/{name}")
	public ResponseEntity<?> searchStudentByName(Pageable pageable, @PathVariable("name") String name){
		log.debug("REST request to search Student by name :{}", name);
		
		try {
			Page<Student> studentPage = studentService.searchStudentByName(pageable, name);
			HttpHeaders headers = PaginationUtil
					.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), studentPage);
			return ResponseEntity.ok().headers(headers).body(studentPage.getContent());
		} catch (Exception e) {
			log.error("Logging error while searching students by name :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}		
	}
	
	
	@GetMapping("/student/{id}/courses")
	public ResponseEntity<?> getStudentCourses(@PathVariable("id") String id) throws Exception{
		log.debug("REST request to search Student courses :{}", id);
		try {
			List<Course> studentCourses = studentService.getStudentCourses(id);
			return ResponseEntity.ok(studentCourses);
		} catch (Exception e) {
			log.error("Logging error while find student courses :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	
	@PatchMapping("/student/{id}/course")
	public ResponseEntity<?> enrollStudentInCourse(@PathVariable("id") String id, @RequestBody List<String> courseIds){
		log.debug("REST request to enroll student :{} in courses :{}", id, courseIds);
		try {
			studentService.enrollStudentInCourses(id, courseIds);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			log.error("Logging error while find student courses :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@GetMapping("/student/list")
	public ResponseEntity<?> getStudentsByIds(@RequestBody List<String> ids){
		log.debug("REST request to get student by ids :{}", ids);
		try {
			List<Student> studentByIds = studentService.getStudentByIds(ids);
			return ResponseEntity.ok(studentByIds);
		} catch (Exception e) {
			log.error("Logging error while finding students by ids :{}", e.getMessage());
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
		
}
