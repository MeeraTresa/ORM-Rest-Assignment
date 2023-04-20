package com.CMPE275.ORMRestAssignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Employee {

    @EmbeddedId
    @JsonUnwrapped
    private EmployeeId employeeId;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    private String title;
    private Address address;

    @ManyToOne
    @JoinColumn(name="employerId", nullable=false, updatable = false, insertable = false, referencedColumnName = "id")
    @JsonIgnoreProperties({"address","employees"})
    private Employer employer;

    @ManyToOne
    @JsonIgnoreProperties({"address","employer","manager","reports","collaborators","collaboratorOf"})
    private Employee manager;

    @OneToMany(mappedBy="manager")
    @JsonIgnoreProperties({"address","employer","manager","reports","collaborators","collaboratorOf"})
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
    @JsonIgnoreProperties({"address","employer","manager","reports","collaborators","collaboratorOf"})
    private List<Employee> collaborators;

    @ManyToMany(mappedBy = "collaborators")
    @JsonIgnore
    private List<Employee> collaboratorOf;

    public Employee() {}

    //Copy constructor for employee deletion usecase
    public Employee(Employee employee){
        this.setEmployeeId(employee.getEmployeeId());
        this.setName(employee.getName());
        this.setAddress(employee.getAddress());
        this.setTitle(employee.getTitle());
        this.setEmail(employee.getEmail());
        this.setManager(employee.getManager());
        this.setEmployer(employee.getEmployer());
        List<Employee> collaboratorsCopy = new ArrayList<>(employee.getCollaborators());
        this.setCollaborators(collaboratorsCopy);
        List<Employee> reportsCopy = new ArrayList<>(employee.getReports());
        this.setReports(employee.getReports());
        List<Employee> collaboratorOfCopy = new ArrayList<>(employee.getCollaboratorOf());
        this.setCollaboratorOf(employee.getCollaboratorOf());
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getReports() {
        return reports;
    }

    public void setReports(List<Employee> reports) {
        this.reports = reports;
    }

    public List<Employee> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Employee> collaborators) {
        this.collaborators = collaborators;
    }

    public List<Employee> getCollaboratorOf() {
        return collaboratorOf;
    }

    public void setCollaboratorOf(List<Employee> collaboratorOf) {
        this.collaboratorOf = collaboratorOf;
    }

}