package sap.faculty_management_system.service;

import org.springframework.stereotype.Service;
import sap.faculty_management_system.dto.CourseDTO;
import sap.faculty_management_system.model.Course;
import sap.faculty_management_system.model.Student;
import sap.faculty_management_system.repository.CourseRepository;
import sap.faculty_management_system.repository.StudentRepository;
import sap.faculty_management_system.response.EnrollmentResponse;
import sap.faculty_management_system.util.DTOConverter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<CourseDTO> getAll() {
        return courseRepository.findAll().stream()
                .map(DTOConverter::convertCourseToDTO).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getTopCourses(int number) {
        List<CourseDTO> courseDTOList = courseRepository.findAll().stream().map(DTOConverter::convertCourseToDTO).collect(Collectors.toList());

        return courseDTOList.stream().sorted(new SortCoursesByEnrollments()).limit(number).collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponse enroll(Long courseId, Long studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            return new EnrollmentResponse(false, "No such course found.");
        }
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            return new EnrollmentResponse(false, "No such student found.");
        }
        //TODO: to check when already enrolled
        boolean isCourseEnrolled = studentOptional.get().getEnrollments().stream()
                .map(a -> a.getId() == courseId)
                .findFirst()
                .orElse(false);
        if (isCourseEnrolled) {
            return new EnrollmentResponse(false, "Student is already enrolled in this course.");
        }
        Student student = studentOptional.get();
        student.addCourse(courseOptional.get());
        studentRepository.save(student);

        return new EnrollmentResponse(true, "Student is now enrolled in the course.");
    }

    private static class SortCoursesByEnrollments implements Comparator<CourseDTO> {
        @Override
        public int compare(CourseDTO o1, CourseDTO o2) {
            if (o1.getTotalEnrollments() == o2.getTotalEnrollments()) {
                return 0;
            }
            return o1.getTotalEnrollments() > o2.getTotalEnrollments() ? -1 : 1;
        }
    }
}
