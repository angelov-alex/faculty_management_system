package sap.facultymanagementsystem.service;

import sap.facultymanagementsystem.dto.TeacherDTO;
import sap.facultymanagementsystem.request.TeacherRequest;
import sap.facultymanagementsystem.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    List<TeacherDTO> getAll();
    TeacherResponse addOrUpdateTeacher(TeacherRequest request);
    List<TeacherDTO> getTopTeachers();

    List<TeacherDTO> getTopTeachers(int number);

    List<TeacherDTO> teacherListReport();
}
