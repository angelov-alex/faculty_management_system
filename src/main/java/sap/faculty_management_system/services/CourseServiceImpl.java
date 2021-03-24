package sap.faculty_management_system.services;

import org.springframework.stereotype.Service;
import sap.faculty_management_system.models.Course;
import sap.faculty_management_system.dtos.CourseDTO;
import sap.faculty_management_system.repositories.CourseRepository;

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
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setCourseLeader(course.getCourseLeader());
        courseDTO.setCredit(course.getCredit());
        courseDTO.setEnrolledStudents(course.getEnrolledStudents());
        return courseDTO;
    }
}
