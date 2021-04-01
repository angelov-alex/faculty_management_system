package sap.facultymanagementsystem.service;

import sap.facultymanagementsystem.dto.StudentDTO;
import sap.facultymanagementsystem.request.StudentRequest;
import sap.facultymanagementsystem.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAll();

    StudentResponse addOrUpdateStudent(StudentRequest request);
}
