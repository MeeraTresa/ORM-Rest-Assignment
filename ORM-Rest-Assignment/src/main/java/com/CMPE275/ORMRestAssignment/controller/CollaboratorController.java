package com.CMPE275.ORMRestAssignment.controller;


import com.CMPE275.ORMRestAssignment.exception.BadRequestException;
import com.CMPE275.ORMRestAssignment.exception.RecordDoesNotExistException;
import com.CMPE275.ORMRestAssignment.service.CollaboratorService;
import com.CMPE275.ORMRestAssignment.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/")
public class CollaboratorController {
    @Autowired
    private CollaboratorService collaboratorService;
    @PutMapping(
            path = "collaborators/{employerId1}/{employeeId1}/{employerId2}/{employeeId2}",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> updateCollaborator(
            @PathVariable Long employerId1,
            @PathVariable Long employeeId1,
            @PathVariable Long employerId2,
            @PathVariable Long employeeId2,
            @RequestParam(required = false) String format
    ) throws RecordDoesNotExistException {
        return new ResponseEntity<>(collaboratorService.
                updateCollaborator(employerId1,
                        employeeId1,
                        employerId2,
                        employeeId2,
                        format),
                Util.setContentTypeAndReturnHeaders(format),
                HttpStatus.OK);
    }

    /**
     * Path:collaborators/{employerId1}/{employeeId1}/{employerId2}/{employeeId2}&format={json |
     * xml }
     * Method: DELETE
     * This request removes the collaboration relation between the two employees. No entities
     * returned.
     * ● If either employee does not exist, return 404.
     * ● If the two employees are not collaborators, return 404, or 400 for any other request error.
     * Otherwise,
     * ● Remove this collaboration relation. Return HTTP code 200 and a meaningful text
     * message if all is successful.
     * @param employerId1
     * @param employeeId1
     * @param employerId2
     * @param employeeId2
     * @param format
     * @return
     * @throws RecordDoesNotExistException
     * @throws BadRequestException
     */
    @DeleteMapping(
            path = "collaborators/{employerId1}/{employeeId1}/{employerId2}/{employeeId2}",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> deleteCollaborator(
            @PathVariable Long employerId1,
            @PathVariable Long employeeId1,
            @PathVariable Long employerId2,
            @PathVariable Long employeeId2,
            @RequestParam(required = false) String format
    ) throws RecordDoesNotExistException, BadRequestException {
        return new ResponseEntity<>(collaboratorService.deleteCollaborator(employerId1,
                employeeId1,
                employerId2,
                employeeId2,
                format),
                Util.setContentTypeAndReturnHeaders(format),
                HttpStatus.OK);
    }
}
