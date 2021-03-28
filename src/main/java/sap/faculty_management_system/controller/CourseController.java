package sap.faculty_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import sap.faculty_management_system.dto.CourseDTO;
import sap.faculty_management_system.request.CourseRequest;
import sap.faculty_management_system.request.DelistRequest;
import sap.faculty_management_system.request.EnrollmentRequest;
import sap.faculty_management_system.response.CourseResponse;
import sap.faculty_management_system.response.DelistResponse;
import sap.faculty_management_system.response.EnrollmentResponse;
import sap.faculty_management_system.service.CourseService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getCourses() {
        List<CourseDTO> result = service.getAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/top/{input}")
    public ResponseEntity<List<CourseDTO>> getTopCourses(@PathVariable String input) {
        int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CourseDTO> result = service.getTopCourses(number);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CourseResponse> addCourse(@Valid @RequestBody CourseRequest request) {

        CourseResponse response = service.addCourse(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentResponse> enroll(@RequestBody EnrollmentRequest request) {

        if (ObjectUtils.isEmpty(request) || null == request.getCourseId() || null == request.getStudentId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EnrollmentResponse response = service.enroll(request);
        if (!response.isEnrolled()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delist")
    public ResponseEntity<DelistResponse> delist(@RequestBody DelistRequest request) {

        if (ObjectUtils.isEmpty(request) || null == request.getCourseId() || null == request.getStudentId()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DelistResponse response = service.delist(request);
        if (!response.isDeleted()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
