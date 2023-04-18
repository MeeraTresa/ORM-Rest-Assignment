package com.CMPE275.ORMRestAssignment.service;

import com.CMPE275.lab2.entity.Address;
import com.CMPE275.lab2.entity.Employer;
import com.CMPE275.lab2.exception.BadRequestException;
import com.CMPE275.lab2.exception.RecordDoesNotExistException;
import com.CMPE275.lab2.model.EmployerModel;
import com.CMPE275.lab2.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class EmployerService {
    /***
     * The Employer Repository
     */
    @Autowired
    private EmployerRepository employerRepository;

    /**
     * Method to create an Employer
     * Calls the repository.save() method
     * @param employerModel
     * @return
     */
    public Employer createEmployer(EmployerModel employerModel, String format) throws BadRequestException{
        Employer employer = new Employer();
        employer.setName(employerModel.getName());
        employer.setDescription(employerModel.getDescription());
        Address address = new Address();
        address.setCity(employerModel.getCity());
        address.setState(employerModel.getState());
        address.setStreet(employerModel.getStreet());
        address.setZip(employerModel.getZip());
        employer.setAddress(address);
        try{
            return employerRepository.save(employer);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMessage(),format);
        }

    }

    /**
     * Method to retrieve employer details based on ID
     * @param id
     * @param format
     * @return
     * @throws RecordDoesNotExistException
     */
    public Employer findEmployer(Long id, String format) throws RecordDoesNotExistException {
        return employerRepository.findById(id).orElseThrow(() ->
                new RecordDoesNotExistException(
                        String.format("Employer with id: %s does not exist", id),
                        format
                )
        );
    }

    /**
     * If there is still any employee belonging to this employer, return 400.
     * If the employer with the given ID does not exist, return 404.
     * No entities returned. - HTTP code 200
     * @param id
     * @param format
     * @return
     * @throws RecordDoesNotExistException
     * @throws BadRequestException
     */

    public Employer deleteEmployer(Long id, String format) throws RecordDoesNotExistException, BadRequestException {
        Employer employer = findEmployer(id, format);
        if (employer.getEmployees() != null && !employer.getEmployees().isEmpty()) {
            throw new BadRequestException(
                    String.format("Employer with id: %s has one employee, hence cannot delete",
                            id),
                    format);
        }
        employerRepository.deleteById(id);
        return employer;
    }
}