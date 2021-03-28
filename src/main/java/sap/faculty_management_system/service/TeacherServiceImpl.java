package sap.faculty_management_system.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.model.Teacher;
import sap.faculty_management_system.repository.TeacherRepository;
import sap.faculty_management_system.request.TeacherRequest;
import sap.faculty_management_system.response.TeacherResponse;
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

        return teacherDTOList.stream().sorted(new SortTeachersByNumOfStudents()).collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getTopTeachers(int number) {
        List<TeacherDTO> teacherDTOList = teacherRepository.findAll().stream().map(DTOConverter::convertTeacherToDTO).collect(Collectors.toList());

        return teacherDTOList.stream().sorted(new SortTeachersByNumOfStudents()).limit(number).collect(Collectors.toList());
    }

    @Override
    public TeacherResponse addOrUpdateTeacher(TeacherRequest request) {
        if (ObjectUtils.isEmpty(request) || request.getName() == null || request.getRank() == null || request.getName().isEmpty()) {
            return new TeacherResponse(false, "Missing input parameter/s.");
        }
        //TODO: to make update teacher instead of error
        List<Teacher> existingTeacherByName = teacherRepository.findAllByName(request.getName());
        if (!existingTeacherByName.isEmpty()) {
            return new TeacherResponse(false, String.format("Teacher with name %s already exist. ", request.getName()));
        }
        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setRank(request.getRank());
        teacherRepository.save(teacher);
        return new TeacherResponse(true, String.format("Teacher %s with rank %s was created successfully.", request.getName(), request.getRank()));
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
