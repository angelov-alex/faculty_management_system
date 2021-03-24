package sap.faculty_management_system.services;

import sap.faculty_management_system.dtos.CourseDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAll();
}
