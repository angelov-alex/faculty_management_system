package sap.faculty_management_system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
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

    /**
     * The method returns all teachers in the database sorted alphabetically.
     *
     * @return List of all teachers converted in DTO
     */
    @Override
    public List<TeacherDTO> getAll() {
        return teacherRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(DTOConverter::convertTeacherToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Add new teacher in the database. If existing, it updates its properties: Rank
     *
     * @param request
     * @return TeacherResponse with boolean if it was created/updated and a message
     */
    @Override
    public TeacherResponse addOrUpdateTeacher(TeacherRequest request) {
        LOGGER.trace("START {}", request);
        TeacherResponse teacherResponse = new TeacherResponse();
        if (ObjectUtils.isEmpty(request)
                || request.getName() == null
                || request.getRank() == null
                || request.getName().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + (request == null ? "Null" : request.toString()));
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

    /**
     * Return all teachers sorted by total assigned students in its courses from top to bottom
     *
     * @return List of all teachers converted in DTO
     */
    @Override
    public List<TeacherDTO> getTopTeachers() {
        List<TeacherDTO> teacherDTOList = teacherRepository.findAll().stream().map(DTOConverter::convertTeacherToDTO).collect(Collectors.toList());

        return teacherDTOList.stream().sorted(new SortTeachersByNumOfStudents()).collect(Collectors.toList());
    }

    /**
     * Return a specific number of teachers sorted by total assigned students in its courses from top to bottom (ex. top 3 teachers)
     *
     * @param number
     * @return List of all teachers converted in DTO
     */
    @Override
    public List<TeacherDTO> getTopTeachers(int number) {
        List<TeacherDTO> teacherDTOList = teacherRepository.findAll().stream().map(DTOConverter::convertTeacherToDTO).collect(Collectors.toList());

        return teacherDTOList.stream().sorted(new SortTeachersByNumOfStudents()).limit(number).collect(Collectors.toList());
    }

    private static class SortTeachersByNumOfStudents implements Comparator<TeacherDTO> {
        /**
         * Compare two specific teachers by the total enrolled students in the courses they lead.
         *
         * @param teacherDTO1
         * @param teacherDTO2
         * @return An int value with -1 if the first teacher has more students, 1 if the second teacher has more students and 0 if they are equal
         */
        @Override
        public int compare(TeacherDTO teacherDTO1, TeacherDTO teacherDTO2) {
            if (teacherDTO1.getTotalNumOfStudents() == teacherDTO2.getTotalNumOfStudents()) {
                return 0;
            }
            return teacherDTO1.getTotalNumOfStudents() > teacherDTO2.getTotalNumOfStudents() ? -1 : 1;
        }
    }
}
