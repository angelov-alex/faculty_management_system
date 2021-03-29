package sap.faculty_management_system.service;

import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.request.TeacherRequest;
import sap.faculty_management_system.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    List<TeacherDTO> getAll();
    TeacherResponse addOrUpdateTeacher(TeacherRequest request);
    List<TeacherDTO> getTopTeachers();

    List<TeacherDTO> getTopTeachers(int number);
}
