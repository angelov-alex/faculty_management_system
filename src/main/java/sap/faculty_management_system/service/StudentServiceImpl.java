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
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDTO> getAll() {

        return studentRepository.findAll().stream().map(DTOConverter::convertStudentToDTO).collect(Collectors.toList());
    }

    @Override
    public StudentResponse addStudent(StudentRequest request) {
        if (ObjectUtils.isEmpty(request) || request.getName() == null || request.getAcademicYear() == null || request.getName().isEmpty()) {
            return new StudentResponse(false, "Missing input parameter/s. ");
        }
        List<Student> existingStudentsByName = studentRepository.findAllByName(request.getName());
        if (!existingStudentsByName.isEmpty()) {
            return new StudentResponse(false, String.format("Student with name %s already exist. ", request.getName()));
        }
        Student student = new Student();
        student.setName(request.getName());
        student.setAcademicYear(request.getAcademicYear());
        studentRepository.save(student);
        return new StudentResponse(true, String.format("Student %s in academic year %s was created successfully. ", request.getName(), request.getAcademicYear()));
    }
}
