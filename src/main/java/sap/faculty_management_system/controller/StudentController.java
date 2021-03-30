package sap.faculty_management_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sap.faculty_management_system.dto.StudentDTO;
import sap.faculty_management_system.request.StudentRequest;
import sap.faculty_management_system.response.StudentResponse;
import sap.faculty_management_system.service.StudentService;
import sap.faculty_management_system.util.Constants;

import javax.validation.Valid;
import java.util.List;

/**
 * Includes methods related to students
 */
@Controller
@RequestMapping("/api/students")
public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    /**
     * Request to list all existing students
     *
     * @return List of StudentDTO with student ID, name, academicYear and all enrollments
     */
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        LOGGER.info(Constants.GETTING_ALL_STUDENTS);
        List<StudentDTO> result = service.getAll();
        if (result.isEmpty()) {
            LOGGER.error(Constants.GETTING_ALL_STUDENTS + Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_ALL_STUDENTS);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Add new student in the database. If existing, it updates its properties: Student academic year
     *
     * @param request Student name and Academic Year
     * @return CourseResponse that holds boolean that is true in success and false and error message in case of failure.
     */
    @PostMapping
    public ResponseEntity<StudentResponse> addOrUpdateStudent(@Valid @RequestBody StudentRequest request) {
        LOGGER.info(Constants.REQUEST_TO_REGISTER_NEW_STUDENT);
        StudentResponse response = service.addOrUpdateStudent(request);
        if (!response.isCreated()) {
            LOGGER.error(response.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
