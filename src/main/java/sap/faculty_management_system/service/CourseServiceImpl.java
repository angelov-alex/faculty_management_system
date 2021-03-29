package sap.faculty_management_system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import sap.faculty_management_system.util.Constants;
import sap.faculty_management_system.util.DTOConverter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CreditRepository creditRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository
            , TeacherRepository teacherRepository, CreditRepository creditRepository) {
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
    public CourseResponse addOrUpdateCourse(CourseRequest request) {
        LOGGER.trace("START {}", request);
        CourseResponse courseResponse = new CourseResponse();
        if (ObjectUtils.isEmpty(request)
                || request.getName() == null
                || request.getCourseLeaderId() == null
                || request.getCreditId() == null
                || request.getName().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + request.toString());
            return new CourseResponse(false, Constants.REQUEST_IS_WRONG);
        }

        List<Course> existingCoursesByName = courseRepository.findAllByName(request.getName());
        Course course = new Course();

        if (!existingCoursesByName.isEmpty()) {
            course = existingCoursesByName.get(0);
            courseResponse.setMessage(String.format("Course %s was updated successfully", request.getName()));
        } else {
            course.setName(request.getName());
            courseResponse.setMessage(String.format("Course %s was created successfully.", request.getName()));
        }

        Optional<Credit> credit = creditRepository.findById(request.getCreditId());
        if (!credit.isPresent()) {
            LOGGER.error(String.format(Constants.NO_SUCH_S_FOUND, Constants.CREDIT) + request.toString());
            return new CourseResponse(false, String.format(Constants.NO_SUCH_S_FOUND, Constants.CREDIT));
        }
        course.setCredit(credit.get());

        Optional<Teacher> teacher = teacherRepository.findById(request.getCourseLeaderId());
        if (!teacher.isPresent()) {
            LOGGER.error(String.format(Constants.NO_SUCH_S_FOUND, Constants.TEACHER) + request.toString());
            return new CourseResponse(false, String.format(Constants.NO_SUCH_S_FOUND, Constants.TEACHER));
        }
        course.setCourseLeader(teacher.get());
        courseRepository.save(course);
        courseResponse.setCreated(true);
        LOGGER.trace(Constants.SUCCESS + " + END {}", request);
        return courseResponse;
    }

    @Override
    public List<CourseDTO> getTopCourses() {
        List<CourseDTO> courseDTOList = courseRepository.findAll()
                .stream()
                .map(DTOConverter::convertCourseToDTO)
                .collect(Collectors.toList());

        return courseDTOList.stream().sorted(new SortCoursesByEnrollments()).collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getTopCourses(int number) {
        List<CourseDTO> courseDTOList = courseRepository.findAll()
                .stream()
                .map(DTOConverter::convertCourseToDTO)
                .collect(Collectors.toList());

        return courseDTOList.stream().sorted(new SortCoursesByEnrollments()).limit(number).collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponse enroll(EnrollmentRequest request) {

        LOGGER.trace("START {}", request);
        if (ObjectUtils.isEmpty(request)
                || request.getCourseId() == null
                || request.getStudentId() == null
                || request.getCourseId().isEmpty()
                || request.getStudentId().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + request.toString());
            return new EnrollmentResponse(false, Constants.REQUEST_IS_WRONG);
        }
        Optional<Course> courseOptional = courseRepository.findById(request.getCourseId());
        if (!courseOptional.isPresent()) {
            LOGGER.error(String.format(Constants.NO_SUCH_S_FOUND, Constants.COURSE) + request.toString());
            return new EnrollmentResponse(false, String.format(Constants.NO_SUCH_S_FOUND, Constants.COURSE));
        }
        Optional<Student> studentOptional = studentRepository.findById(request.getStudentId());
        if (!studentOptional.isPresent()) {
            LOGGER.error(String.format(Constants.NO_SUCH_S_FOUND, Constants.STUDENT) + request.toString());
            return new EnrollmentResponse(false, String.format(Constants.NO_SUCH_S_FOUND, Constants.STUDENT));
        }

        boolean isCourseEnrolled = isCourseEnrolled(request.getCourseId(), studentOptional);

        if (isCourseEnrolled) {
            return new EnrollmentResponse(false, String.format("Student %s is already enrolled in course %s."
                    , studentOptional.get().getName(), courseOptional.get().getName()));
        }
        Student student = studentOptional.get();
        student.addCourse(courseOptional.get());
        studentRepository.save(student);
        LOGGER.trace(Constants.SUCCESS + " + END {}", request);
        return new EnrollmentResponse(true, String.format("Student %s is now enrolled in %s."
                , student.getName(), courseOptional.get().getName()));
    }

    @Override
    public DelistResponse delist(DelistRequest request) {
        LOGGER.trace("START {}", request);
        if (ObjectUtils.isEmpty(request)
                || request.getCourseId() == null
                || request.getStudentId() == null
                || request.getCourseId().isEmpty()
                || request.getStudentId().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + request.toString());
            return new DelistResponse(false, Constants.REQUEST_IS_WRONG);
        }

        Optional<Course> courseOptional = courseRepository.findById(request.getCourseId());

        if (!courseOptional.isPresent()) {
            LOGGER.error(String.format(Constants.NO_SUCH_S_FOUND, Constants.COURSE) + request.toString());
            return new DelistResponse(false, String.format(Constants.NO_SUCH_S_FOUND, Constants.COURSE));
        }
        Optional<Student> studentOptional = studentRepository.findById(request.getStudentId());
        if (!studentOptional.isPresent()) {
            LOGGER.error(String.format(Constants.NO_SUCH_S_FOUND, Constants.STUDENT) + request.toString());
            return new DelistResponse(false, String.format(Constants.NO_SUCH_S_FOUND, Constants.STUDENT));
        }
        boolean isCourseEnrolled = isCourseEnrolled(request.getCourseId(), studentOptional);

        if (!isCourseEnrolled) {
            return new DelistResponse(false, String.format("Student %s is not currently enrolled for %s. It cannot be delisted."
                    , studentOptional.get().getName(), courseOptional.get().getName()));
        }
        Student student = studentOptional.get();
        student.removeCourse(courseOptional.get());
        studentRepository.save(student);
        LOGGER.trace(Constants.SUCCESS + " + END {}", request);
        return new DelistResponse(true, String.format("Student %s is delisted from course %s."
                , studentOptional.get().getName(), courseOptional.get().getName()));
    }

    private boolean isCourseEnrolled(String courseId, Optional<Student> studentOptional) {
        List<Course> courseList = studentOptional.get().getEnrollments();
        for (Course a : courseList) {
            if (a.getId().equals(courseId)) {
                return true;
            }
        }
        return false;
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
