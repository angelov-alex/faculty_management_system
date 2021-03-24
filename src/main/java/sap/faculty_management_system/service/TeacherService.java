package sap.faculty_management_system.service;

import sap.faculty_management_system.dto.TeacherDTO;

import java.util.List;

public interface TeacherService {
    List<TeacherDTO> getAll();

    List<TeacherDTO> getTopTeachers();

}
