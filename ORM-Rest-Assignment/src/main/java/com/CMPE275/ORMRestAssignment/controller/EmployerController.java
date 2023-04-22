package com.CMPE275.ORMRestAssignment.controller;


import com.CMPE275.ORMRestAssignment.entity.Employer;
import com.CMPE275.ORMRestAssignment.exception.BadRequestException;
import com.CMPE275.ORMRestAssignment.exception.RecordDoesNotExistException;
import com.CMPE275.ORMRestAssignment.model.EmployerModel;
import com.CMPE275.ORMRestAssignment.service.EmployerService;
import com.CMPE275.ORMRestAssignment.util.Util;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/")
public class EmployerController {
    @Autowired
    private EmployerService employerService;

    /***
     * employer?name=XX&description=YY&street=ZZ&...&format={json | xml }
     * This API creates an employer object.
     * ● For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in
     * as query parameters. Only name is required.
     * ● The request returns the full form of the newly created employer object in the given format
     * in its HTTP payload, including all attributes. (Please note this differs from the generally
     * recommended practice of only returning the ID.)
     * ● If the request is invalid, e.g., missing required parameters, the HTTP status code should
     * be 400 (it’s OK to return more detailed error code, e.g., 409 for object conflicts);
     * otherwise 200.
     *
     * @param name
     * @param description
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param format
     * @return
     */

    @PostMapping( path = "employer",
                  produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employer> createEmployer(
            @RequestParam(required = true) @NotBlank @NotNull String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String zip,
            @RequestParam(required = false) String format   ) throws BadRequestException {
        EmployerModel employerModel = new EmployerModel();
        employerModel.setName(name);
        employerModel.setDescription(description);
        employerModel.setStreet(street);
        employerModel.setCity(city);
        employerModel.setState(state);
        employerModel.setZip(zip);
        //Validate the name parameter
        if(name.trim().length()==0) throw new BadRequestException("Employer name cannot be all whitespaces",format);
        return new ResponseEntity<>(employerService.createEmployer(employerModel, format), Util.setContentTypeAndReturnHeaders(format), HttpStatus.OK);
    }

    /**
     * Path:employer/{id}?format={json | xml }
     * Method: GET
     * This returns a full employer object with the given ID in the given format.
     * ● All existing fields, including the optional ones should be returned.
     * ● If the employer of the given user ID does not exist, the HTTP return code should be 404,
     * 400 if the request has other errors, otherwise, 200.
     * @param id
     * @param format
     * @return
     * @throws RecordDoesNotExistException
     */
    @GetMapping(path = "employer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employer> getEmployee(
            @PathVariable(required = true) String id,
            @RequestParam(required = false) String format
    ) throws RecordDoesNotExistException {
        return new ResponseEntity<>(employerService.findEmployer(id, format),
                Util.setContentTypeAndReturnHeaders(format), HttpStatus.OK);
    }

    /***
     *This method deletes the employer object with the given ID. No entities returned.
     * ● If there is still any employee belonging to this employer, return 400.
     * ● If the employer with the given ID does not exist, return 404.
     * ● Return HTTP code 200.
     * @param id
     * @param format
     * @return
     * @throws RecordDoesNotExistException
     * @throws BadRequestException
     */
    @DeleteMapping(path = "employer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployer(
            @PathVariable(required = true) String id,
            @RequestParam(required = false) String format) throws RecordDoesNotExistException, BadRequestException {
        employerService.deleteEmployer(id, format);

    }

    /**
     * Path: employer/{id}?name=XX&description=YY&street=ZZ&...&format={json | xml }
     * Method: PUT
     * This API updates an employer object and returns its full form.
     * ● For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in
     * as query parameters. Only name is required.
     * ● Similar to the get method, the request returns the updated employer object, including all
     * attributes in JSON. If the employer ID does not exist, 404 should be returned. If required
     * parameters are missing, return 400 instead. Otherwise, return 200.
     * @param id
     * @param name
     * @param description
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param format
     * @return
     * @throws RecordDoesNotExistException
     */
    @PutMapping(path = "employer/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employer> updateEmployer(
            @PathVariable(required = true) String id,
            @RequestParam(required = true) @NotBlank @NotNull String name,
            @RequestParam(required= false) String description,
            @RequestParam(required= false) String street,
            @RequestParam(required= false) String city,
            @RequestParam(required= false) String state,
            @RequestParam(required= false) String zip,
            @RequestParam(required = false) String format) throws RecordDoesNotExistException,BadRequestException {
        EmployerModel employerModel = new EmployerModel(name, description,
                street, city, state, zip);
        //validate the name parameter
        if(name.trim().length()==0) throw new BadRequestException("Employer name cannot be all whitespaces",format);
        return new ResponseEntity<>(employerService.updateEmployer(id, employerModel, format),
                Util.setContentTypeAndReturnHeaders(format), HttpStatus.OK);
    }
}
