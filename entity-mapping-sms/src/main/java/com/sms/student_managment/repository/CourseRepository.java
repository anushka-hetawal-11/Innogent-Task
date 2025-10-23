package com.sms.student_managment.repository;

import com.sms.student_managment.dto.CourseStudentCountDTO;
import com.sms.student_managment.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

    @Query("SELECT new com.sms.student_managment.dto.CourseStudentCountDTO(c.course_name, COUNT(s)) " +
            "FROM Course c LEFT JOIN c.students s " +
            "GROUP BY c.id, c.course_name")
    List<CourseStudentCountDTO> getStudentCountPerCourse();

    @Query("select c from Course c LEFT JOIN c.students s where s IS NULL")
    List<Course> getCourseWithNoStudents();

    @Query("SELECT new com.sms.student_managment.dto.CourseStudentCountDTO(c.id, c.course_name, c.instructor ,COUNT(s)) " +
            "from Course c LEFT JOIN c.students s " +
            "GROUP BY c.id, c.course_name, c.instructor")
    List<CourseStudentCountDTO> getCourseWithStudentCount();

    @Query("SELECT new com.sms.student_managment.dto.CourseStudentCountDTO(c.id, c.course_name, c.instructor ,COUNT(s)) " +
            "from Course c LEFT JOIN c.students s " +
            "GROUP BY c.id, c.course_name, c.instructor " +
            "ORDER BY COUNT(s) DESC")
    Page<CourseStudentCountDTO> findTopCourses(Pageable pageable);
}

