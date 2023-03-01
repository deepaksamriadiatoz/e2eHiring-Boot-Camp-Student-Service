package com.e2ehiring.bootcamp.student.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private String id;
    private String courseName;
    private String courseDescription;
    private String prerequisites;
    private String syllabus;
    private String instructorId;
    private Integer duration;
    private Instant createdDate = Instant.now();
    private Instant lastModifiedDate;

}