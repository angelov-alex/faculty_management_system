package sap.faculty_management_system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.model.Teacher;
import sap.faculty_management_system.repository.TeacherRepository;
import sap.faculty_management_system.request.TeacherRequest;
import sap.faculty_management_system.response.TeacherResponse;
import sap.faculty_management_system.util.Constants;
import sap.faculty_management_system.util.DTOConverter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherDTO> getAll() {
        return teacherRepository.findAll().stream().map(DTOConverter::convertTeacherToDTO).collect(Collectors.toList());
    }

    @Override
    public TeacherResponse addOrUpdateTeacher(TeacherRequest request) {
        LOGGER.trace("START {}", request);
        TeacherResponse teacherResponse = new TeacherResponse();
        if (ObjectUtils.isEmpty(request)
                || request.getName() == null
                || request.getRank() == null
                || request.getName().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + request.toString());
            return new TeacherResponse(false, Constants.REQUEST_IS_WRONG);
        }

        List<Teacher> existingTeacherByName = teacherRepository.findAllByName(request.getName());
        Teacher teacher = new Teacher();

        if (!existingTeacherByName.isEmpty()) {
            teacher = existingTeacherByName.get(0);
            teacherResponse.setMessage(String.format("Teacher %s with rank %s was updated successfully.", request.getName(), request.getRank()));
        } else {
            teacher.setName(request.getName());
            teacherResponse.setMessage(String.format("Teacher %s with rank %s was created successfully.", request.getName(), request.getRank()));
        }
        teacher.setRank(request.getRank());
        teacherRepository.save(teacher);
        teacherResponse.setCreated(true);
        LOGGER.trace(Constants.SUCCESS + " + END {}", request);
        return teacherResponse;
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
