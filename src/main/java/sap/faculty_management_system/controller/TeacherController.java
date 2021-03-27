package sap.faculty_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.dto.TeacherDTO2;
import sap.faculty_management_system.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

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

    //TODO: change with input parameter
    @GetMapping("/top")
    public ResponseEntity<List<TeacherDTO>> getTopTeachers() {
        List<TeacherDTO> result = service.getTopTeachers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    //TODO: ADD Teacher method

    //TODO: fix the method (probably will not be used)
    @GetMapping("/top/query")
    public ResponseEntity<List<TeacherDTO2>> getQuery1() {
        List<TeacherDTO2> result = service.getTopTeachers().stream().map(this::toDTO2).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //TODO: probably delete
    private TeacherDTO2 toDTO2(TeacherDTO teacherDTO) {
        return new TeacherDTO2(teacherDTO.getName(), teacherDTO.getRank(), teacherDTO.getTotalNumOfLeadCourses());
    }

}

