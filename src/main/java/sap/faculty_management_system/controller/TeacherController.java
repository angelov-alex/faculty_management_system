package sap.faculty_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.service.TeacherService;

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

    @GetMapping("/top")
    public ResponseEntity<List<TeacherDTO>> getTopTeachers() {
        List<TeacherDTO> result = service.getTopTeachers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

