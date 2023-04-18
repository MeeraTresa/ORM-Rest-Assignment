package com.CMPE275.ORMRestAssignment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeModel {
    @NotBlank
    private String name;
    @Email
    private String email;
    private String title;
    private String street; // e.g., 100 Main ST
    private String city;
    private String state;
    private String zip;
    private Long managerId;

}
