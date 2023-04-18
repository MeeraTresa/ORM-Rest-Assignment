package com.CMPE275.ORMRestAssignment.service;

import com.CMPE275.lab2.entity.Address;
import com.CMPE275.lab2.entity.Employee;
import com.CMPE275.lab2.entity.EmployeeId;
import com.CMPE275.lab2.entity.Employer;
import com.CMPE275.lab2.exception.BadRequestException;
import com.CMPE275.lab2.exception.RecordDoesNotExistException;
import com.CMPE275.lab2.model.EmployeeModel;
import com.CMPE275.lab2.repository.EmployeeRepository;
import com.CMPE275.lab2.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployerRepository employerRepository;
    public Employee createEmployee(Long employerId, EmployeeModel employeeModel, String format) throws RecordDoesNotExistException, BadRequestException {
        //Create an Employee Entity
        Employee employee = new Employee();
        //Find the Employer entity corresponding to the passed employerId
        Optional<Employer> employer = employerRepository.findById(employerId);
        //If Employer not found, throw the Record not Found exception
        if(employer.isEmpty()) {
            throw new RecordDoesNotExistException(String.format("Employer with id:%s not found",
                    employerId), format);
        }
        //Retrieve details of the Manager for an employee, if passed
        Long managerId = employeeModel.getManagerId();
        if (managerId!=null) {
            //Call an inner function to find the manager details
            Employee manager = findManager(employerId, managerId, format);
            employee.setManager(manager);
        }
        //Set the Employer in Employee entity
        employee.setEmployer(employer.get());
        //Since auto increment is not working, find the next employee ID in sequence
        //Revisit this custom query
        Long nextVal = employeeRepository.getNextSequence()+1;
        EmployeeId eId = new EmployeeId(nextVal, employerId);
        employee.setEmployeeId(eId);

        employee.setName(employeeModel.getName());
        employee.setEmail(employeeModel.getEmail());
        employee.setTitle(employeeModel.getTitle());

        // Create address object
        Address address = new Address();
        address.setCity(employeeModel.getCity());
        address.setState(employeeModel.getState());
        address.setStreet(employeeModel.getStreet());
        address.setZip(employeeModel.getZip());
        employee.setAddress(address);
        try{
            return employeeRepository.save(employee);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMessage(),format);
        }

    }

    private Employee findManager(Long employerId, Long managerId, String format) throws RecordDoesNotExistException {
        EmployeeId employeeId = new EmployeeId();
        employeeId.setId(managerId);
        employeeId.setEmployerId(employerId);
        Optional<Employee> optionalManager = employeeRepository.findById(employeeId);
        if (optionalManager.isEmpty()) {
            throw new RecordDoesNotExistException(String.format("Manager with id:%s not " +
                    "found", managerId), format);
        }
        return optionalManager.get();
    }

    public Employee findEmployee(Long employerId, Long id, String format) throws RecordDoesNotExistException {
        EmployeeId employeeId = new EmployeeId(id, employerId);
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new RecordDoesNotExistException(String.format("Employee does not exist for employerId: {} and" +
                    "emplyeeId:{}", employerId, employeeId), format);
        }
        return optionalEmployee.get();
    }
}
