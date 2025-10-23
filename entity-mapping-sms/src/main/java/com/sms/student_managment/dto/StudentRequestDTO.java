package com.sms.student_managment.dto;

import lombok.Data;
import java.util.Set;

@Data
public class StudentRequestDTO {
    private String name;
    private String email;
    private String city;
    private Set<Long> courseIds;
}
