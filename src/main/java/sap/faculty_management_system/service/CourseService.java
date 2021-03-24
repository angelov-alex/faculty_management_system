package sap.faculty_management_system.service;

import sap.faculty_management_system.dto.CourseDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAll();
}
