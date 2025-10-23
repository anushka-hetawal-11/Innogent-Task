package com.sms.student_managment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String course_name;
    private String instructor;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
//    @JsonBackReference
    private Set<Student> students = new HashSet<>();
}
