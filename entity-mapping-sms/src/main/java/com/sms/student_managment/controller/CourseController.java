package com.sms.student_managment.controller;

import com.sms.student_managment.dto.CourseRequestDTO;
import com.sms.student_managment.dto.CourseResponseDTO;
import com.sms.student_managment.dto.CourseStudentCountDTO;
import com.sms.student_managment.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDTO> saveCourse(@RequestBody CourseRequestDTO courseRequestDTO){
        return ResponseEntity.ok(courseService.saveCourse(courseRequestDTO));
    }


    @PutMapping("/{id}/instructor")
    public ResponseEntity<CourseResponseDTO> updateCourseInstructor(@PathVariable Long id,@RequestBody Map<String,String> body){
        String instructor = body.get("instructor");
        return ResponseEntity.ok(courseService.updateCourseInstructor(id,instructor));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/student-count")
    public ResponseEntity<List<CourseStudentCountDTO>> getStudentCountPerCourse(){
        return ResponseEntity.ok(courseService.getStudentCountPerCourse());
    }

    @GetMapping("/null-students")
    public ResponseEntity<List<CourseResponseDTO>> getCourseWithNoStudents(){
        return ResponseEntity.ok(courseService.getCourseWithNoStudents());
    }

    @GetMapping("/course-student-count")
    public ResponseEntity<List<CourseStudentCountDTO>> getCourseWithStudentCount(){
        return ResponseEntity.ok(courseService.getCourseWithStudentCount());
    }

    @GetMapping("/top-courses")
    public ResponseEntity<List<CourseStudentCountDTO>> getTopNCourses(@RequestParam int n){
        return ResponseEntity.ok(courseService.findTopCourses(n));
    }
}
