package com.sms.student_managment.dto;

import lombok.Data;
import java.util.Set;

@Data
public class StudentResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String city;
    private Set<String> courseNames;
}
