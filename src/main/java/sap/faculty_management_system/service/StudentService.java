package sap.faculty_management_system.service;

import sap.faculty_management_system.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAll();
}
