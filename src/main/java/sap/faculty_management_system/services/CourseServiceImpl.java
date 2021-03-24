package sap.faculty_management_system.services;

import org.springframework.stereotype.Service;
import sap.faculty_management_system.dtos.CourseDTO;
import sap.faculty_management_system.repositories.CourseRepository;

import java.util.List;
import java.util.stream.Collectors;

import static sap.faculty_management_system.util.DTOConverter.convertCourseToDTO;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CourseDTO> getAll() {
        return repository.findAll().stream()
                .map(a -> convertCourseToDTO(a)).collect(Collectors.toList());
    }
}
