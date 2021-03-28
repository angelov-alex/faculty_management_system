package sap.faculty_management_system.service;

import sap.faculty_management_system.dto.CourseDTO;
import sap.faculty_management_system.request.CourseRequest;
import sap.faculty_management_system.request.DelistRequest;
import sap.faculty_management_system.request.EnrollmentRequest;
import sap.faculty_management_system.response.CourseResponse;
import sap.faculty_management_system.response.DelistResponse;
import sap.faculty_management_system.response.EnrollmentResponse;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAll();

    CourseResponse addCourse(CourseRequest request);

    EnrollmentResponse enroll(EnrollmentRequest request);

    DelistResponse delist(DelistRequest request);

    List<CourseDTO> getTopCourses(int number);
}
