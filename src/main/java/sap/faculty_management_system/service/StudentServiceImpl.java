package sap.faculty_management_system.service;

import org.springframework.stereotype.Service;
import sap.faculty_management_system.dto.StudentDTO;
import sap.faculty_management_system.repository.StudentRepository;
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
}
