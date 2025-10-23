package com.sms.student_managment.service;

import com.sms.student_managment.model.Student;
import com.sms.student_managment.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudent(Long id){
        return studentRepository.findById(id).orElseThrow(()->new RuntimeException("Student not found with id "+id));
    }

    public void deleteStudent(Long id){
         studentRepository.deleteById(id);
    }
}
