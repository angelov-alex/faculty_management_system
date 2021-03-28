package sap.faculty_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.request.TeacherRequest;
import sap.faculty_management_system.response.TeacherResponse;
import sap.faculty_management_system.service.TeacherService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getTeachers() {
        List<TeacherDTO> result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/top/{input}")
    public ResponseEntity<List<TeacherDTO>> getTopTeachers(@PathVariable String input) {
        int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<TeacherDTO> result = service.getTopTeachers(number);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeacherResponse> addTeacher(@Valid @RequestBody TeacherRequest request) {

        TeacherResponse response = service.addTeacher(request);
        if (!response.isCreated()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    //TODO: fix the method (probably will not be used)
//    @GetMapping("/top/query")
//    public ResponseEntity<List<TeacherDTO2>> getQuery1() {
//        List<TeacherDTO2> result = service.getTopTeachers(number).stream().map(this::toDTO2).collect(Collectors.toList());
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    //TODO: probably delete
//    private TeacherDTO2 toDTO2(TeacherDTO teacherDTO) {
//        return new TeacherDTO2(teacherDTO.getName(), teacherDTO.getRank(), teacherDTO.getTotalNumOfLeadCourses());
//    }

}

