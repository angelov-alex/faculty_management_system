package sap.faculty_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sap.faculty_management_system.dto.StudentDTO;
import sap.faculty_management_system.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getStudents() {
        List<StudentDTO> result = service.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
