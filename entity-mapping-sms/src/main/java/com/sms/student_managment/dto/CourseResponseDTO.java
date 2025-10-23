package com.sms.student_managment.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CourseResponseDTO {
    private Long id;
    private String course_name;
    private String instructor;
    private Set<String> studentNames;
}
