package sap.faculty_management_system.services;

import org.springframework.stereotype.Service;
import sap.faculty_management_system.dtos.TeacherDTO;

import java.util.List;

public interface TeacherService {
    List<TeacherDTO> getAll();

    List<TeacherDTO> getTopTeachers();

}
