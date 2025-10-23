package com.sms.student_managment.service;

import com.sms.student_managment.dto.StudentRequestDTO;
import com.sms.student_managment.dto.StudentResponseDTO;
import com.sms.student_managment.model.Course;
import com.sms.student_managment.model.Student;
import com.sms.student_managment.repository.CourseRepository;
import com.sms.student_managment.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public StudentResponseDTO saveStudent(StudentRequestDTO studentRequestDTO){
        Student student = new Student();
        student.setName(studentRequestDTO.getName());
        student.setEmail(studentRequestDTO.getEmail());
        student.setCity(studentRequestDTO.getCity());

        Set<Course> courses = new HashSet<>();
        if(studentRequestDTO.getCourseIds() != null){
            for(Long courseID : studentRequestDTO.getCourseIds()){
                Course course = courseRepository.findById(courseID)
                        .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseID));
                courses.add(course);
            }
        }
        student.setCourses(courses);

        Student savedStudent = studentRepository.save(student);
        return convertToResponseDTO(savedStudent);
    }

    @Transactional
    @Modifying
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.setName(studentRequestDTO.getName());
        student.setEmail(studentRequestDTO.getEmail());
        student.setCity(studentRequestDTO.getCity());

        if(studentRequestDTO.getCourseIds() != null){
            Set<Course> courses = new HashSet<>();
            for(Long courseId : studentRequestDTO.getCourseIds()){
                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new RuntimeException("Course not found with courseId: " + courseId));
                courses.add(course);
            }
            student.setCourses(courses);
        }

        Student updated = studentRepository.save(student);
        return convertToResponseDTO(updated);
    }

    public List<StudentResponseDTO> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    private StudentResponseDTO convertToResponseDTO(Student student) {
        StudentResponseDTO response = new StudentResponseDTO();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setCity(student.getCity());

        Set<String> courseNames = student.getCourses().stream()
                .map(Course::getCourse_name)
                .collect(Collectors.toSet());
        response.setCourseNames(courseNames);

        return response;
    }

    public List<StudentResponseDTO> getStudentsByCourseName(String courseName){
        List<Student> students = studentRepository.findByCourseName(courseName);
        return students.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getStudentWithNoCourse(){
      return  studentRepository.getStudentWithNoCourse().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public List<StudentResponseDTO> findByCityAndInstructor(String city ,String instructor){
       return studentRepository.findByCityAndInstructor(city, instructor).stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public StudentResponseDTO getStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(()->new RuntimeException("Student not found with id "+id));
        return convertToResponseDTO(student);
    }

    public void deleteStudent(Long id){
         studentRepository.deleteById(id);
    }
}
