package sap.faculty_management_system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sap.faculty_management_system.dto.StudentDTO;
import sap.faculty_management_system.model.Student;
import sap.faculty_management_system.repository.StudentRepository;
import sap.faculty_management_system.request.StudentRequest;
import sap.faculty_management_system.response.StudentResponse;
import sap.faculty_management_system.util.Constants;
import sap.faculty_management_system.util.DTOConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDTO> getAll() {
        return studentRepository.findAll().stream().map(DTOConverter::convertStudentToDTO).collect(Collectors.toList());
    }

    @Override
    public StudentResponse addOrUpdateStudent(StudentRequest request) {
        LOGGER.trace("START {}", request);
        StudentResponse studentResponse = new StudentResponse();
        if (ObjectUtils.isEmpty(request)
                || request.getName() == null
                || request.getAcademicYear() == null
                || request.getName().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + request.toString());
            return new StudentResponse(false, Constants.REQUEST_IS_WRONG);
        }
        List<Student> existingStudentsByName = studentRepository.findAllByName(request.getName());
        Student student = new Student();

        if (!existingStudentsByName.isEmpty()) {
            student = existingStudentsByName.get(0);
            studentResponse.setMessage(String.format("Student %s in academic year %s was updated successfully."
                    , request.getName(), request.getAcademicYear()));
        } else {
            student.setName(request.getName());
            studentResponse.setMessage(String.format("Student %s in academic year %s was created successfully."
                    , request.getName(), request.getAcademicYear()));
        }
        student.setAcademicYear(request.getAcademicYear());
        studentRepository.save(student);
        studentResponse.setCreated(true);
        LOGGER.trace(Constants.SUCCESS + " + END {}", request);
        return studentResponse;
    }
}
