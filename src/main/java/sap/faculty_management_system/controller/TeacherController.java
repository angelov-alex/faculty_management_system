package sap.faculty_management_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.request.TeacherRequest;
import sap.faculty_management_system.response.TeacherResponse;
import sap.faculty_management_system.service.TeacherService;
import sap.faculty_management_system.util.Constants;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/teachers")
public class TeacherController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        LOGGER.info(Constants.GETTING_ALL_TEACHERS);
        List<TeacherDTO> result = service.getAll();
        if (result.isEmpty()) {
            LOGGER.error(Constants.GETTING_ALL_TEACHERS + Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_ALL_TEACHERS);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeacherResponse> addTeacher(@Valid @RequestBody TeacherRequest request) {
        LOGGER.info(Constants.REQUEST_TO_REGISTER_NEW_TEACHER);
        TeacherResponse response = service.addOrUpdateTeacher(request);

        if (!response.isCreated()) {
            LOGGER.error(response.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/top/{input}")
    public ResponseEntity<List<TeacherDTO>> getTopTeachers(@PathVariable String input) {
        LOGGER.info(Constants.GETTING_TOP_TEACHERS);
        int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            LOGGER.error(Constants.GETTING_TOP_TEACHERS + Constants.REQUEST_IS_WRONG);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<TeacherDTO> result = service.getTopTeachers(number);
        if (result.isEmpty()) {
            LOGGER.error(Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_TOP_TEACHERS);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

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
//    //TODO: fix the method (probably will not be used)
//    @GetMapping("/query")
//    public ResponseEntity<List<TeacherDTO2>> getQuery1() {
//        List<TeacherDTO2> result = service.getTopTeachers().stream().map(a -> convertTeacherReportToDTO(a)).collect(Collectors.toList());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}

