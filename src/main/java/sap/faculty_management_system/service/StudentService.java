package sap.faculty_management_system.service;

import sap.faculty_management_system.dto.StudentDTO;
import sap.faculty_management_system.request.StudentRequest;
import sap.faculty_management_system.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAll();

    StudentResponse addOrUpdateStudent(StudentRequest request);
}
