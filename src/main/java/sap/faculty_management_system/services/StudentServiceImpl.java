package sap.faculty_management_system.services;

import org.springframework.stereotype.Service;
import sap.faculty_management_system.dtos.StudentDTO;
import sap.faculty_management_system.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static sap.faculty_management_system.util.DTOConverter.convertStudentToDTO;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<StudentDTO> getAll() {

        return repository.findAll().stream().map(s -> convertStudentToDTO(s)).collect(Collectors.toList());
    }
}
