package sap.facultymanagementsystem.service;

import sap.facultymanagementsystem.dto.CourseDTO;
import sap.facultymanagementsystem.request.CourseRequest;
import sap.facultymanagementsystem.request.DelistRequest;
import sap.facultymanagementsystem.request.EnrollmentRequest;
import sap.facultymanagementsystem.response.CourseResponse;
import sap.facultymanagementsystem.response.DelistResponse;
import sap.facultymanagementsystem.response.EnrollmentResponse;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAll();

    CourseResponse addOrUpdateCourse(CourseRequest request);

    List<CourseDTO> getTopCourses();

    List<CourseDTO> getTopCourses(int number);

    EnrollmentResponse enroll(EnrollmentRequest request);

    DelistResponse delist(DelistRequest request);
}
