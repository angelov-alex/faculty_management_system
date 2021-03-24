package sap.faculty_management_system.services;

import sap.faculty_management_system.dtos.StudentDTO;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAll();
}
