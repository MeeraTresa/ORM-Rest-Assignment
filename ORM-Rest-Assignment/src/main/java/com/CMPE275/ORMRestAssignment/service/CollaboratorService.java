package com.CMPE275.ORMRestAssignment.service;


import com.CMPE275.ORMRestAssignment.entity.Employee;
import com.CMPE275.ORMRestAssignment.entity.EmployeeId;
import com.CMPE275.ORMRestAssignment.exception.BadRequestException;
import com.CMPE275.ORMRestAssignment.exception.RecordDoesNotExistException;
import com.CMPE275.ORMRestAssignment.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollaboratorService {
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * This makes the two employees with the given IDs collaborators with each other. No entities
     * returned.
     * If either employee does not exist, return 404, and 400 for any other request error.
     * If the two employees are already collaborators, do nothing, just return 200. Otherwise,
     * Record this collaboration relation. If all is successful, return HTTP code 200 and any
     * informative text message in the given format in the HTTP payload.
     * @param employerId1
     * @param employeeId1
     * @param employerId2
     * @param employeeId2
     * @param format
     * @throws RecordDoesNotExistException
     * @return
     */
    public String updateCollaborator(Long employerId1, Long employeeId1,
                                     Long employerId2, Long employeeId2,
                                     String format) throws RecordDoesNotExistException {
        //Find the employees
        Employee employee1 = findEmployee(employeeId1, employerId1, format);
        Employee employee2 = findEmployee(employeeId2, employerId2, format);
        //Check if these are existing collaborators
        List<Employee> existingCollaborators = employee1.getCollaborators();
        if(existingCollaborators.stream().anyMatch(e -> e.getEmployeeId().getId()== employeeId2
                && e.getEmployeeId().getEmployerId() == employerId2)) {
            return String.format("Already a collaborator");
        }
        //Must never happen - the below case
        List<Employee> existingCollaborators2 = employee2.getCollaborators();
        if(existingCollaborators2.stream().anyMatch(e -> e.getEmployeeId().getId()== employeeId1
                && e.getEmployeeId().getEmployerId() == employerId1)) {
            return String.format("Already a collaborator");
        }
        employee1.getCollaborators().add(employee2);
        employee2.getCollaborators().add(employee1);
        //Error handling on DAO operation - please check
        try{
            employeeRepository.saveAll(List.of(employee1, employee2));
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return String.format("Request for collaboration successful");
    }

    /**
     * Method to find employees corresponding to their IDs
     * @param employeeId
     * @param employerId
     * @param format
     * @return
     * @throws RecordDoesNotExistException
     */
    private Employee findEmployee(Long employeeId, Long employerId, String format) throws RecordDoesNotExistException {
        return employeeRepository.
                findById(new EmployeeId(employeeId, employerId))
                .orElseThrow(() ->
                        new RecordDoesNotExistException(
                                String.format("Employee with id:%s and employerId:%s" +
                                        "does not exist", employeeId, employerId),
                                format
                        ));
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

    public String deleteCollaborator(Long employerId1, Long employeeId1, Long employerId2, Long employeeId2, String format) throws RecordDoesNotExistException, BadRequestException {
        Employee employee1 = findEmployee(employeeId1, employerId1, format);
        Employee employee2 = findEmployee(employeeId2, employerId2, format);
        List<Employee> existingCollaborators = employee1.getCollaborators();
        if(existingCollaborators.stream().noneMatch(e -> e.getEmployeeId().getId() == employeeId2
                && e.getEmployeeId().getEmployerId() == employerId2)) {
            throw new BadRequestException(
                    String.format("Employee with id:%s and employer id:%s not a collaborator of" +
                                    "employee with id:%s and employerId:%s",
                            employeeId1,
                            employerId1,
                            employeeId2,
                            employerId2),
                    format
            );
        }
        List<Employee> existingCollaborators2 = employee2.getCollaborators();
        if(existingCollaborators2.stream().noneMatch(e -> e.getEmployeeId().getId()== employeeId1
                && e.getEmployeeId().getEmployerId() == employerId1)) {
            throw new BadRequestException(
                    String.format("Employee with id:%s and employer id:%s not a collaborator of" +
                                    "employee with id:%s and employerId:%s",
                            employeeId2,
                            employerId2,
                            employeeId1,
                            employerId1),
                    format
            );
        }
        employee1.getCollaborators().remove(employee2);
        employee2.getCollaborators().remove(employee1);
        employeeRepository.saveAll(List.of(employee1, employee2));
        return String.format("Collaboration removed successfully");
    }
}
