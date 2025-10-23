package com.sms.student_managment.repository;

import com.sms.student_managment.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long>{

    @Query("SELECT s from Student s JOIN s.courses c where c.course_name = :courseName")
    List<Student> findByCourseName(@Param("courseName") String courseName);

    @Query("SELECT s from Student s LEFT JOIN s.courses c where c IS NULL")
    List<Student> getStudentWithNoCourse();

    @Query("SELECT DISTINCT s from Student s JOIN s.courses c where s.city = :city AND c.instructor = :instructor")
    List<Student> findByCityAndInstructor(@Param("city") String city, @Param("instructor") String instructor);
}
