package sap.facultymanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sap.facultymanagementsystem.dto.StudentDTO;
import sap.facultymanagementsystem.request.StudentRequest;
import sap.facultymanagementsystem.response.StudentResponse;
import sap.facultymanagementsystem.service.StudentService;
import sap.facultymanagementsystem.util.Constants;

import javax.validation.Valid;
import java.util.List;

/**
 * Includes methods related to students
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentService service;

    @Autowired
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
