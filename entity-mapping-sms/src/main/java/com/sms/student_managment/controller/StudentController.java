package com.sms.student_managment.controller;

import com.sms.student_managment.dto.StudentRequestDTO;
import com.sms.student_managment.dto.StudentResponseDTO;
import com.sms.student_managment.model.Student;
import com.sms.student_managment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public StudentResponseDTO saveStudent(@RequestBody StudentRequestDTO studentRequestDTO){
        return studentService.saveStudent(studentRequestDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDTO studentRequestDTO){
         StudentResponseDTO updated = studentService.updateStudent(id,studentRequestDTO);
         return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(){
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/by-course") //--/students/by-course?courseName=Spring Boot
    public ResponseEntity<List<StudentResponseDTO>> getStudentByCourseName(@RequestParam String courseName ){
        return ResponseEntity.ok(studentService.getStudentsByCourseName(courseName));
    }

    @GetMapping("/null-courses")
    public ResponseEntity<List<StudentResponseDTO>> getStudentWithNoCourse(){
        return ResponseEntity.ok(studentService.getStudentWithNoCourse());
    }

    @GetMapping("/by-cityInstructor")  //---http://localhost:8080/students/by-cityInstructor?city=indore&instructor=Code%20with%20Durgesh
    public ResponseEntity<List<StudentResponseDTO>> findByCityAndInstructor(@RequestParam String city, @RequestParam String instructor){
        return ResponseEntity.ok(studentService.findByCityAndInstructor(city, instructor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
