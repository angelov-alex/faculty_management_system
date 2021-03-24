package sap.faculty_management_system.services;

import org.springframework.stereotype.Service;
import sap.faculty_management_system.dtos.TeacherDTO;
import sap.faculty_management_system.repositories.TeacherRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static sap.faculty_management_system.util.DTOConverter.convertTeacherToDTO;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherDTO> getAll() {
        return teacherRepository.findAll().stream().map(c -> convertTeacherToDTO(c)).collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getTopTeachers() {
        List<TeacherDTO> teacherDTOList = teacherRepository.findAll().stream().map(c -> convertTeacherToDTO(c)).collect(Collectors.toList());

        return teacherDTOList.stream().sorted(new SortTeachersByNumOfStudents()).limit(3).collect(Collectors.toList());
    }

    private static class SortTeachersByNumOfStudents implements Comparator<TeacherDTO> {
        @Override
        public int compare(TeacherDTO o1, TeacherDTO o2) {
            return o1.getTotalNumOfStudents() > o2.getTotalNumOfStudents() ? -1 : 1;
        }
    }
}
