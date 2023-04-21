package com.CMPE275.ORMRestAssignment.repository;


import com.CMPE275.ORMRestAssignment.entity.Employee;
import com.CMPE275.ORMRestAssignment.entity.EmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, EmployeeId> {
    @Query(value = "SELECT COALESCE(MAX(employee_id),0) FROM employee WHERE employee.employer_id = ?1", nativeQuery = true)
    Long getNextSequence( String employerId);

}
