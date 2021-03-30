package sap.faculty_management_system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import sap.faculty_management_system.util.Constants;

import javax.validation.Valid;
import java.util.List;


/**
 * Includes all methods related to courses including enrollments, delist
 */
@Controller
@RequestMapping("/api/courses")
public class CourseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    /**
     * Request to list all existing courses
     *
     * @return List of CourseDTOs with course ID, name, credits and total enrollments
     */
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        LOGGER.info(Constants.GETTING_ALL_COURSES);
        List<CourseDTO> result = service.getAll();
        if (result.isEmpty()) {
            LOGGER.warn(Constants.GETTING_ALL_COURSES + Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_ALL_COURSES);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Adds new course or update existing one.
     *
     * @param request contains name of course, courseLeaderId and creditId
     * @return CourseResponse that holds boolean that is true in success and false and error message in case of failure.
     */
    @PostMapping
    public ResponseEntity<CourseResponse> addOrUpdateCourse(@Valid @RequestBody CourseRequest request) {
        LOGGER.info(Constants.REQUEST_TO_REGISTER_NEW_COURSE);
        CourseResponse response = service.addOrUpdateCourse(request);

        if (!response.isCreated()) {
            LOGGER.error(response.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Request to get all courses sorted by total enrollments listed from top to bottom.
     *
     * @return Returns all courses sorted by total enrollments from top to bottom
     */
    @GetMapping("/top")
    public ResponseEntity<List<CourseDTO>> getTopCourses() {

        LOGGER.info(Constants.GETTING_TOP_COURSES);

        List<CourseDTO> result = service.getTopCourses();
        if (result.isEmpty()) {
            LOGGER.error(Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_TOP_COURSES);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Request to get top N courses based on the user input.
     *
     * @param input Number of top courses to be displayed.
     * @return Returns a specific number of courses sorted by total enrollments from top to bottom (ex. top 3 courses)
     */
    @GetMapping("/top/{input}")
    public ResponseEntity<List<CourseDTO>> getTopCourses(@PathVariable String input) {

        LOGGER.info(Constants.GETTING_TOP_COURSES);
        int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            LOGGER.error(Constants.GETTING_TOP_COURSES + Constants.REQUEST_IS_WRONG);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CourseDTO> result = service.getTopCourses(number);
        if (result.isEmpty()) {
            LOGGER.error(Constants.NO_RECORDS_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LOGGER.info(Constants.SUCCESS + Constants.GETTING_TOP_COURSES);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Request to enroll a specific student into a specified course.
     *
     * @param request CourseId and StudentId
     * @return ResponseEntity with the respective HTTP status.
     */
    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentResponse> enroll(@RequestBody EnrollmentRequest request) {
        LOGGER.info(Constants.REQUEST_ENROLL_STUDENT_INTO_COURSE);

        if (ObjectUtils.isEmpty(request) || null == request.getCourseId() || null == request.getStudentId()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EnrollmentResponse response = service.enroll(request);
        if (!response.isEnrolled()) {
            LOGGER.error(response.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete request to delist a specific student from a specified course.
     *
     * @param request CourseId and StudentId
     * @return ResponseEntity with the respective HTTP status.
     */
    @DeleteMapping("/delist")
    public ResponseEntity<DelistResponse> delist(@RequestBody DelistRequest request) {
        LOGGER.info(Constants.REQUEST_DELIST_STUDENT_FROM_COURSE);
        if (ObjectUtils.isEmpty(request) || null == request.getCourseId() || null == request.getStudentId()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DelistResponse response = service.delist(request);
        if (!response.isDeleted()) {
            LOGGER.error(response.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info(response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
