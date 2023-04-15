package com.CMPE275.ORMRestAssignment.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Employer {
    @Id
    private long id; //primary key
    @Column(nullable = false, unique = true)
    private String name;// required and must be unique
    private String description;
    private Address address;
    @OneToMany(mappedBy="employer")
    private Set<Employee> employees;
}
