package sap.facultymanagementsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sap.facultymanagementsystem.dto.StudentDTO;
import sap.facultymanagementsystem.model.Student;
import sap.facultymanagementsystem.repository.StudentRepository;
import sap.facultymanagementsystem.request.StudentRequest;
import sap.facultymanagementsystem.response.StudentResponse;
import sap.facultymanagementsystem.util.Constants;
import sap.facultymanagementsystem.util.DTOConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * The method returns all Students in the database sorted alphabetically.
     *
     * @return List of all students converted in DTO
     */
    @Override
    public List<StudentDTO> getAll() {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(DTOConverter::convertStudentToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Add new student in the database. If existing, it updates its properties: Academic year
     *
     * @param request
     * @return StudentResponse with boolean if it was created/updated and a message
     */
    @Override
    public StudentResponse addOrUpdateStudent(StudentRequest request) {
        LOGGER.trace("START {}", request);
        StudentResponse studentResponse = new StudentResponse();
        if (ObjectUtils.isEmpty(request)
                || request.getName() == null
                || request.getAcademicYear() == null
                || request.getName().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + (request == null ? "Null" : request.toString()));
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
