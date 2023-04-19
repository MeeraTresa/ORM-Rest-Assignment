package com.CMPE275.ORMRestAssignment.service;

import com.CMPE275.ORMRestAssignment.entity.Address;
import com.CMPE275.ORMRestAssignment.entity.Employer;
import com.CMPE275.ORMRestAssignment.exception.BadRequestException;
import com.CMPE275.ORMRestAssignment.exception.RecordDoesNotExistException;

import com.CMPE275.ORMRestAssignment.model.EmployerModel;
import com.CMPE275.ORMRestAssignment.repository.EmployerRepository;
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

    public Employer updateEmployer(Long id, EmployerModel employerModel, String format) throws RecordDoesNotExistException {
        Employer employer = findEmployer(id, format);
        if(employer != null) {
            if (employerModel.getName() != null && !employerModel.getName().isEmpty()) {
                employer.setName(employerModel.getName());
            }
            if (employerModel.getDescription() != null && !employerModel.getDescription().isEmpty()) {
                employer.setDescription(employerModel.getDescription());
            }
            Address addressToBeUpdated = employer.getAddress();
            if (employerModel.getStreet()!=null && !employerModel.getStreet().isEmpty()) {
                addressToBeUpdated.setStreet(employerModel.getStreet());
            }
            if (employerModel.getCity() != null && !employerModel.getCity().isEmpty()) {
                addressToBeUpdated.setCity(employerModel.getCity());
            }
            if (employerModel.getState() != null && !employerModel.getState().isEmpty()) {
                addressToBeUpdated.setState(employerModel.getState());
            }
            if (employerModel.getZip() != null && !employerModel.getZip().isEmpty()) {
                addressToBeUpdated.setZip(employerModel.getZip());
            }
            employer.setAddress(addressToBeUpdated);
        }
        return employerRepository.save(employer);
    }
}
