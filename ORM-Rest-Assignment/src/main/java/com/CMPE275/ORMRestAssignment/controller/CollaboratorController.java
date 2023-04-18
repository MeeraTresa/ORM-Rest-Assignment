package com.CMPE275.ORMRestAssignment.controller;


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
}
