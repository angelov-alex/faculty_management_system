package sap.faculty_management_system.service;

import org.springframework.stereotype.Service;
import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.repository.TeacherRepository;
import sap.faculty_management_system.util.DTOConverter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherDTO> getAll() {
        return teacherRepository.findAll().stream().map(DTOConverter::convertTeacherToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getTopTeachers() {
        List<TeacherDTO> teacherDTOList = teacherRepository.findAll().stream().map(DTOConverter::convertTeacherToDTO).collect(Collectors.toList());

        return teacherDTOList.stream().sorted(new SortTeachersByNumOfStudents()).limit(3).collect(Collectors.toList());
    }

    private static class SortTeachersByNumOfStudents implements Comparator<TeacherDTO> {
        @Override
        public int compare(TeacherDTO o1, TeacherDTO o2) {
            if (o1.getTotalNumOfStudents() == o2.getTotalNumOfStudents()) {
                return 0;
            }
            return o1.getTotalNumOfStudents() > o2.getTotalNumOfStudents() ? -1 : 1;
        }
    }
}
