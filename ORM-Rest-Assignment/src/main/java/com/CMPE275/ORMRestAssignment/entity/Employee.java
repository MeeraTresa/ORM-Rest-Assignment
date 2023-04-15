package com.CMPE275.ORMRestAssignment.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity

public class Employee {

    @EmbeddedId
    private EmployeeId employeeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String title;
    private Address address;

    @ManyToOne
    @JoinColumn(name="employerId", nullable=false, updatable = false, insertable = false, referencedColumnName = "id")
    private Employer employer;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="managerId", referencedColumnName="employee_id"),
            @JoinColumn(name="managerEmployerId", referencedColumnName="employerId")
    })
    private Employee manager;

    @OneToMany(mappedBy="manager")
    private List<Employee> reports; // director reports who have the current employee as their manager.

    @ManyToMany
    @JoinTable(name="collaboration",
            joinColumns = {
                    @JoinColumn(name = "employeeId", referencedColumnName = "employee_id"),
                    @JoinColumn(name = "employerId", referencedColumnName = "employerId")
            },
            inverseJoinColumns={
                @JoinColumn(name="c_EmployeeId", referencedColumnName = "employee_id"),
                @JoinColumn(name = "c_EmployerId", referencedColumnName = "employerId")
            }
    )
    private List<Employee> collaborators;

    @ManyToMany(mappedBy = "collaborators")
    private List<Employee> collaboratorOf;

    protected Employee() {}

}