package sap.facultymanagementsystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sap.facultymanagementsystem.dto.StudentDTO;
import sap.facultymanagementsystem.dto.TeacherDTO;
import sap.facultymanagementsystem.model.User;
import sap.facultymanagementsystem.service.StudentService;
import sap.facultymanagementsystem.service.TeacherService;
import sap.facultymanagementsystem.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Includes all methods related to faculty members - all users both teachers and students
 */
@Controller
@RequestMapping("/api/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final TeacherService teacherService;
    private final StudentService studentService;

    public UserController(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    /**
     * Request to list all existing users in the faculty (both teachers and students)
     *
     * @return List of Users with all their properties (
     * Teachers: id, name, rank, lead courses;
     * Students: id, name, academic year and enrolled courses).
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        LOGGER.info(Constants.GETTING_ALL_USERS);
        List<User> userList = new ArrayList<>();

        LOGGER.trace("Start " + Constants.GETTING_ALL_TEACHERS);
        List<TeacherDTO> teacherDTOList = teacherService.getAll();

        LOGGER.trace("Start " + Constants.GETTING_ALL_STUDENTS);
        List<StudentDTO> studentDTOList = studentService.getAll();

        LOGGER.trace(String.format("Adding to the list all %ss", Constants.TEACHER));
        userList.addAll(teacherDTOList);

        LOGGER.trace(String.format("Adding to the list all %ss", Constants.STUDENT));
        userList.addAll(studentDTOList);

        LOGGER.info(Constants.SUCCESS + Constants.GETTING_ALL_USERS);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

}
