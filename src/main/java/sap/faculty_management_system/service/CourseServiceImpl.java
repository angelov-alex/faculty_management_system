package sap.faculty_management_system.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sap.faculty_management_system.dto.CourseDTO;
import sap.faculty_management_system.model.Course;
import sap.faculty_management_system.model.Credit;
import sap.faculty_management_system.model.Student;
import sap.faculty_management_system.model.Teacher;
import sap.faculty_management_system.repository.CourseRepository;
import sap.faculty_management_system.repository.CreditRepository;
import sap.faculty_management_system.repository.StudentRepository;
import sap.faculty_management_system.repository.TeacherRepository;
import sap.faculty_management_system.request.CourseRequest;
import sap.faculty_management_system.request.DelistRequest;
import sap.faculty_management_system.request.EnrollmentRequest;
import sap.faculty_management_system.response.CourseResponse;
import sap.faculty_management_system.response.DelistResponse;
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
    private final TeacherRepository teacherRepository;
    private final CreditRepository creditRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, CreditRepository creditRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.creditRepository = creditRepository;
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
    public EnrollmentResponse enroll(EnrollmentRequest request) {
        if (ObjectUtils.isEmpty(request)
                || request.getCourseId() == null
                || request.getStudentId() == null
                || request.getCourseId().isEmpty()
                || request.getStudentId().isEmpty()) {
            return new EnrollmentResponse(false, "Missing input parameter/s.");
        }
        Optional<Course> courseOptional = courseRepository.findById(request.getCourseId());
        if (!courseOptional.isPresent()) {
            return new EnrollmentResponse(false, "No such course found.");
        }
        Optional<Student> studentOptional = studentRepository.findById(request.getStudentId());
        if (!studentOptional.isPresent()) {
            return new EnrollmentResponse(false, "No such student found.");
        }

        List<Course> courseList = studentOptional.get().getEnrollments();
        boolean isCourseEnrolled = false;
        for (Course a : courseList) {
            if (a.getId().equals(request.getCourseId())) {
                isCourseEnrolled = true;
                break;
            }
        }
        if (isCourseEnrolled) {
            return new EnrollmentResponse(false, "Student is already enrolled in this course.");
        }
        Student student = studentOptional.get();
        student.addCourse(courseOptional.get());
        studentRepository.save(student);

        return new EnrollmentResponse(true, "Student is now enrolled in the course.");
    }

    @Override
    public DelistResponse delist(DelistRequest request) {
        if (ObjectUtils.isEmpty(request)
                || request.getCourseId() == null
                || request.getStudentId() == null
                || request.getCourseId().isEmpty()
                || request.getStudentId().isEmpty()) {
            return new DelistResponse(false, "Missing input parameter/s.");
        }
        //TODO: to export the repeating code in a separate method
        Optional<Course> courseOptional = courseRepository.findById(request.getCourseId());

        if (!courseOptional.isPresent()) {
            return new DelistResponse(false, "No such course found.");
        }
        Optional<Student> studentOptional = studentRepository.findById(request.getStudentId());
        if (!studentOptional.isPresent()) {
            return new DelistResponse(false, "No such student found.");
        }
        List<Course> courseList = studentOptional.get().getEnrollments();
        boolean isCourseEnrolled = false;
        for (Course a : courseList) {
            if (a.getId().equals(request.getCourseId())) {
                isCourseEnrolled = true;
                break;
            }
        }
        if (!isCourseEnrolled) {
            return new DelistResponse(false, "Student was not enrolled for course " + courseOptional.get().getName());
        }
        Student student = studentOptional.get();
        student.removeCourse(courseOptional.get());
        studentRepository.save(student);
        return new DelistResponse(true, "Student " + studentOptional.get().getName() + " is delisted from course " + courseOptional.get().getName());
    }

    @Override
    public CourseResponse addCourse(CourseRequest request) {
        CourseResponse courseResponse = new CourseResponse();
        if (ObjectUtils.isEmpty(request)
                || request.getName() == null
                || request.getCourseLeaderId() == null
                || request.getCreditId() == null
                || request.getName().isEmpty()) {
            return new CourseResponse(false, "Missing input parameter/s.");
        }

        Course course = new Course();
        List<Course> existingCoursesByName = courseRepository.findAllByName(request.getName());

        if (!existingCoursesByName.isEmpty()) {
            course = existingCoursesByName.get(0);
            courseResponse.setMessage("Course " + request.getName() + " was updated successfully");
        } else {
            course.setName(request.getName());
            courseResponse.setMessage("Course " + request.getName() + " was created successfully");
        }

        Optional<Credit> credit = creditRepository.findById(request.getCreditId());
        if (!credit.isPresent()) {
            return new CourseResponse(false, "No such credit found");
        }
        course.setCredit(credit.get());

        Optional<Teacher> teacher = teacherRepository.findById(request.getCourseLeaderId());
        if (!teacher.isPresent()) {
            return new CourseResponse(false, "No such teacher found");
        }
        course.setCourseLeader(teacher.get());

        courseRepository.save(course);
        courseResponse.setCreated(true);
        return courseResponse;
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
