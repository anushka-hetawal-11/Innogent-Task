package com.sms.student_managment.service;

import com.sms.student_managment.dto.CourseRequestDTO;
import com.sms.student_managment.dto.CourseResponseDTO;
import com.sms.student_managment.dto.CourseStudentCountDTO;
import com.sms.student_managment.model.Course;
import com.sms.student_managment.model.Student;
import com.sms.student_managment.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public CourseResponseDTO saveCourse(CourseRequestDTO courseRequestDTO){
        Course course = new Course();
        course.setCourse_name(courseRequestDTO.getCourse_name());
        course.setInstructor(courseRequestDTO.getInstructor());
        Course saved =  courseRepository.save(course);
        return convertToResponseDTO(saved);
    }

    @Transactional
    @Modifying
    public CourseResponseDTO updateCourseInstructor(Long id , String instructor){
        Course course = courseRepository.findById(id).orElseThrow(()->new RuntimeException("course not found with id: "+id));
        course.setInstructor(instructor);
        Course updated = courseRepository.save(course);
        return convertToResponseDTO(updated);
    }

    public List<CourseResponseDTO> getAllCourses(){
        return courseRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    private CourseResponseDTO convertToResponseDTO(Course course) {
        CourseResponseDTO responseDto = new CourseResponseDTO();
        responseDto.setId(course.getId());
        responseDto.setCourse_name(course.getCourse_name());
        responseDto.setInstructor(course.getInstructor());

        Set<String> studentNames = course.getStudents().stream()
                .map(Student::getName)
                .collect(Collectors.toSet());
        responseDto.setStudentNames(studentNames);

        return responseDto;
    }

    public List<CourseStudentCountDTO> getStudentCountPerCourse() {
        return courseRepository.getStudentCountPerCourse();
    }

    public List<CourseResponseDTO> getCourseWithNoStudents(){
        List<Course> courses = courseRepository.getCourseWithNoStudents();
        return courses.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public List<CourseStudentCountDTO> getCourseWithStudentCount(){
        return courseRepository.getCourseWithStudentCount();
    }

    public List<CourseStudentCountDTO> findTopCourses(int n){
        Pageable pageable = PageRequest.of(0, n);
        return courseRepository.findTopCourses(pageable).getContent();
    }
}
