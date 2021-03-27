package sap.faculty_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import sap.faculty_management_system.dto.CourseDTO;
import sap.faculty_management_system.request.EnrollmentRequest;
import sap.faculty_management_system.response.EnrollmentResponse;
import sap.faculty_management_system.service.CourseService;

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
    //TODO: @DeleteMapping("/{id}/delist/student/{id}")
    //TODO: Create Course

}
