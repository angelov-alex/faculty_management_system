package sap.faculty_management_system.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sap.faculty_management_system.dto.StudentDTO;
import sap.faculty_management_system.model.Student;
import sap.faculty_management_system.repository.StudentRepository;
import sap.faculty_management_system.request.StudentRequest;
import sap.faculty_management_system.response.StudentResponse;
import sap.faculty_management_system.util.DTOConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<StudentDTO> getAll() {

        return repository.findAll().stream().map(DTOConverter::convertStudentToDTO).collect(Collectors.toList());
    }

    @Override
    public StudentResponse addStudent(StudentRequest request) {
        if (ObjectUtils.isEmpty(request) || request.getName() == null || request.getAcademicYear() == null || request.getName().isEmpty()) {
            return new StudentResponse(false, "Missing input parameter/s.");
        }

        Student student = new Student();
        student.setName(request.getName());
        student.setAcademicYear(request.getAcademicYear());
        repository.save(student);
        return new StudentResponse(true, "Student was created successfully.");
    }
}
