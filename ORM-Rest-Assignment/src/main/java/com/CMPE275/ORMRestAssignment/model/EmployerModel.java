package com.CMPE275.ORMRestAssignment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployerModel {
    private String name;// required and must be unique
    private String description;
    private String street; // e.g., 100 Main ST
    private String city;
    private String state;
    private String zip;
}
