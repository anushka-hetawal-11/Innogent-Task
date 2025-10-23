package com.sms.student_managment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseStudentCountDTO {
    private Long id;
    private String courseName;
    private String instructor;
    private Long studentCount;

    public CourseStudentCountDTO(String courseName, Long studentCount) {
        this.courseName = courseName;
        this.studentCount = studentCount;
    }

}