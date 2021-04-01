package sap.facultymanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sap.facultymanagementsystem.dto.TeacherDTO;
import sap.facultymanagementsystem.request.TeacherRequest;
import sap.facultymanagementsystem.response.TeacherResponse;
import sap.facultymanagementsystem.service.TeacherService;
import sap.facultymanagementsystem.util.Constants;

import javax.validation.Valid;
import java.util.List;

/**
 * Includes all methods related to teachers
 */
@Controller
@RequestMapping("/api/teachers")
public class TeacherController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    /**
     * Request to list all existing teachers
     *
     * @return List of TeacherDTO with teacher ID, name, rank and lead courses
     */
    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        LOGGER.info(Constants.GETTING_ALL_TEACHERS);
        List<TeacherDTO> result = service.getAll();
        if (result.isEmpty()) {
            LOGGER.warn(Constants.GETTING_ALL_TEACHERS + Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_ALL_TEACHERS);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Adds new teacher or update existing one.
     *
     * @param request contains name of teacher and its rank
     * @return TeacherResponse that holds boolean that is true in success and false and error message in case of failure.
     */
    @PostMapping
    public ResponseEntity<TeacherResponse> addOrUpdateTeacher(@Valid @RequestBody TeacherRequest request) {
        LOGGER.info(Constants.REQUEST_TO_REGISTER_NEW_TEACHER);
        TeacherResponse response = service.addOrUpdateTeacher(request);

        if (!response.isCreated()) {
            LOGGER.error(response.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Request to get all teachers sorted by total number of students in the courses they lead from top to bottom.
     *
     * @return Returns all teachers sorted by total number of students
     */
    @GetMapping("/top")
    public ResponseEntity<List<TeacherDTO>> getTopTeachers() {
        LOGGER.info(Constants.GETTING_TOP_TEACHERS);
        int number;

        List<TeacherDTO> result = service.getTopTeachers();
        if (result.isEmpty()) {
            LOGGER.error(Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_TOP_TEACHERS);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Request to get top N teachers based on the user input.
     *
     * @param input Number of top teachers to be displayed.
     * @return Returns a specific number of teachers sorted by total students in their courses from top to bottom (ex. top 3 courses)
     */
    @GetMapping("/top/{input}")
    public ResponseEntity<List<TeacherDTO>> getTopTeachers(@PathVariable String input) {
        LOGGER.info("START {}", Constants.GETTING_TOP_TEACHERS);
        int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            LOGGER.error(Constants.GETTING_TOP_TEACHERS + Constants.REQUEST_IS_WRONG + input);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<TeacherDTO> result = service.getTopTeachers(number);
        if (result.isEmpty()) {
            LOGGER.warn(Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_TOP_TEACHERS);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Request to get all teachers leading more than 1 course with more than 5 students in total.
     *
     * @return Returns a list of teachers sorted by total students in their courses from top to bottom
     */
    @GetMapping("/report")
    public ResponseEntity<List<TeacherDTO>> getTeacherSpecificReport() {
        List<TeacherDTO> result = service.teacherListReport();
        if (result.isEmpty()) {
            LOGGER.warn(Constants.GETTING_ALL_TEACHERS + Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_ALL_TEACHERS);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

