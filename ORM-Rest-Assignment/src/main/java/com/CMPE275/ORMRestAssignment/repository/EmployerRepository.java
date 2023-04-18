package com.CMPE275.ORMRestAssignment.repository;


import com.CMPE275.ORMRestAssignment.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

}
