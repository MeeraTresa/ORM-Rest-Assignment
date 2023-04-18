package com.CMPE275.ORMRestAssignment.controller;


import com.CMPE275.ORMRestAssignment.entity.Employee;
import com.CMPE275.ORMRestAssignment.exception.BadRequestException;
import com.CMPE275.ORMRestAssignment.exception.RecordDoesNotExistException;
import com.CMPE275.ORMRestAssignment.model.EmployeeModel;
import com.CMPE275.ORMRestAssignment.service.EmployeeService;
import com.CMPE275.ORMRestAssignment.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /***
     * Path:
     * employer/{employerId}/employee?name=XX&email=ZZ&title=UU&street=VV...managerId=WW&
     * format={json | xml }
     * Method: POST
     * If the request is invalid, e.g., missing required parameters, the HTTP status code should
     * be 400 (if you want to provide detailed error code, e.g., 409 for entity conflicts, that is fine
     * too); if the request is successful, return 200.The request returns the full form of the newly
     * created employee entity in the requested format in its HTTP payload, including all
     * attributes.
     *
     * @param employerId
     * @param name
     * @param email
     * @param title
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param managerId
     * @param format
     * @return
     * @throws RecordDoesNotExistException
     * @throws BadRequestException
     */
    @PostMapping(path = "employer/{employerId}/employee", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employee> createEmployee(
            @PathVariable(required = true) Long employerId,
            @RequestParam(required = true) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String zip,
            @RequestParam(required = false) Long managerId,
            @RequestParam(required = false) String format) throws RecordDoesNotExistException, BadRequestException {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setName(name);
        employeeModel.setEmail(email);
        employeeModel.setTitle(title);
        employeeModel.setStreet(street);
        employeeModel.setCity(city);
        employeeModel.setState(state);
        employeeModel.setZip(zip);
        employeeModel.setManagerId(managerId);
        return new ResponseEntity<>(employeeService.createEmployee(employerId, employeeModel, format),
                Util.setContentTypeAndReturnHeaders(format), HttpStatus.OK);
    }

    /***
     * Path:employer/{employerId}/employee/{id}?format={json | xml }
     * Method: GET
     * This returns the full form of the given employee entity with the given employer ID and employee
     * ID in the given format in its HTTP payload.
     * ● All existing fields, including the employer and list of collaborators should be returned. If
     * the employee of the given user ID does not exist, the HTTP return code should be 404;
     * 400 for other errors, or 200 if successful.
     * @param employerId
     * @param id
     * @param format
     * @return
     * @throws RecordDoesNotExistException
     */
    @GetMapping(path = "employer/{employerId}/employee/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Employee> getEmployee(
            @PathVariable(required = true) Long employerId,
            @PathVariable(required = true) Long id,
            @RequestParam(required = false) String format
    ) throws RecordDoesNotExistException {
        return new ResponseEntity<>(employeeService.findEmployee(employerId, id, format),
                Util.setContentTypeAndReturnHeaders(format), HttpStatus.OK);
    }

}
