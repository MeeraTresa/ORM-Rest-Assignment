package com.CMPE275.ORMRestAssignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerModel {
    private String name;// required and must be unique
    private String description;
    private String street; // e.g., 100 Main ST
    private String city;
    private String state;
    private String zip;
}
