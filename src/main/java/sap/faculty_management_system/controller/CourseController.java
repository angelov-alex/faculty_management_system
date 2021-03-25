package sap.faculty_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sap.faculty_management_system.dto.CourseDTO;
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

    @GetMapping("/top{input}")
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

    @PostMapping("/{cid}/enroll/student/{sid}")
    public ResponseEntity<EnrollmentResponse> enroll(@PathVariable(name = "cid") String courseId,
                                                     @PathVariable(name = "sid") String studentId) {
        //TODO: try parse long
        EnrollmentResponse response = service.enroll(Long.parseLong(courseId), Long.parseLong(studentId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //TODO: @DeleteMapping("/{id}/delist/student/{id}")
}
