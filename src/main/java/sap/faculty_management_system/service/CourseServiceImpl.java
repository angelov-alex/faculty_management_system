package sap.faculty_management_system.service;

import org.springframework.stereotype.Service;
import sap.faculty_management_system.dto.CourseDTO;
import sap.faculty_management_system.repository.CourseRepository;
import sap.faculty_management_system.util.DTOConverter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CourseDTO> getAll() {
        return repository.findAll().stream()
                .map(DTOConverter::convertCourseToDTO).collect(Collectors.toList());
    }
}
