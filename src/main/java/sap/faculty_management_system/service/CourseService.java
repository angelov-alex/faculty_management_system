package sap.faculty_management_system.service;

import sap.faculty_management_system.dto.CourseDTO;
import sap.faculty_management_system.response.EnrollmentResponse;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAll();

    List<CourseDTO> getTopCourses(int number);

    EnrollmentResponse enroll(Long courseId, Long studentId);

}
