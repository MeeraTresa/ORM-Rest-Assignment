package com.CMPE275.ORMRestAssignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EmployeeId implements Serializable {
    @Column(name = "employee_id")
    private long id;
    @Column(nullable = false)
    private Long employerId;

    public EmployeeId(long id, Long employerId, String name) {
        this.id = id;
        this.employerId = employerId;
    }

    public EmployeeId() {}
}
