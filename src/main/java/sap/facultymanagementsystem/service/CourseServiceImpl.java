package sap.facultymanagementsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sap.facultymanagementsystem.dto.CourseDTO;
import sap.facultymanagementsystem.model.Course;
import sap.facultymanagementsystem.model.Credit;
import sap.facultymanagementsystem.model.Student;
import sap.facultymanagementsystem.model.Teacher;
import sap.facultymanagementsystem.repository.CourseRepository;
import sap.facultymanagementsystem.repository.CreditRepository;
import sap.facultymanagementsystem.repository.StudentRepository;
import sap.facultymanagementsystem.repository.TeacherRepository;
import sap.facultymanagementsystem.request.CourseRequest;
import sap.facultymanagementsystem.request.DelistRequest;
import sap.facultymanagementsystem.request.EnrollmentRequest;
import sap.facultymanagementsystem.response.CourseResponse;
import sap.facultymanagementsystem.response.DelistResponse;
import sap.facultymanagementsystem.response.EnrollmentResponse;
import sap.facultymanagementsystem.util.Constants;
import sap.facultymanagementsystem.util.DTOConverter;

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

    /**
     * Returns all courses in the database sorted alphabetically.
     *
     * @return List of all courses mapped to DTO
     */
    @Override
    public List<CourseDTO> getAll() {
        return courseRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(DTOConverter::convertCourseToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Add new course in the database. If existing, it updates its properties: Course Leader or Credit Hours
     *
     * @param request course name, course leader Id and course credit Id
     * @return CourseResponse with boolean if it was created/updated and a message
     */
    @Override
    public CourseResponse addOrUpdateCourse(CourseRequest request) {
        LOGGER.trace("START {}", request);
        CourseResponse courseResponse = new CourseResponse();
        if (ObjectUtils.isEmpty(request)
                || request.getName() == null
                || request.getCourseLeaderId() == null
                || request.getCreditId() == null
                || request.getName().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + (request == null ? "Null" : request.toString()));
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

    /**
     * Returns all courses sorted by total enrollments from top to bottom
     *
     * @return List of all courses converted in DTO
     */
    @Override
    public List<CourseDTO> getTopCourses() {
        List<CourseDTO> courseDTOList = courseRepository.findAll()
                .stream()
                .map(DTOConverter::convertCourseToDTO)
                .collect(Collectors.toList());

        return courseDTOList.stream().sorted(new SortCoursesByEnrollments()).collect(Collectors.toList());
    }

    /**
     * Returns a specific number of courses sorted by total enrollments from top to bottom (ex. top 3 courses)
     *
     * @param number Number of desired courses to be displayed
     * @return List of all courses converted in DTO
     */
    @Override
    public List<CourseDTO> getTopCourses(int number) {
        List<CourseDTO> courseDTOList = courseRepository.findAll()
                .stream()
                .map(DTOConverter::convertCourseToDTO)
                .collect(Collectors.toList());

        return courseDTOList.stream().sorted(new SortCoursesByEnrollments()).limit(number).collect(Collectors.toList());
    }

    /**
     * Enroll a specific student in a specified course.
     *
     * @param request CourseId and StudentId
     * @return EnrollmentResponse with boolean if it was enrolled and an error message
     */
    @Override
    public EnrollmentResponse enroll(EnrollmentRequest request) {

        LOGGER.trace("START {}", request);
        if (ObjectUtils.isEmpty(request)
                || request.getCourseId() == null
                || request.getStudentId() == null
                || request.getCourseId().isEmpty()
                || request.getStudentId().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + (request == null ? "Null" : request.toString()));
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

    /**
     * Delist specific student from a specified course.
     *
     * @param request CourseId and StudentId
     * @return DelistResponse with boolean if it was delisted and an error message
     */
    @Override
    public DelistResponse delist(DelistRequest request) {
        LOGGER.trace("START {}", request);
        if (ObjectUtils.isEmpty(request)
                || request.getCourseId() == null
                || request.getStudentId() == null
                || request.getCourseId().isEmpty()
                || request.getStudentId().isEmpty()) {
            LOGGER.error(Constants.REQUEST_IS_WRONG + (request == null ? "Null" : request.toString()));
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

    /**
     * Check if certain student is enrolled in a specific course.
     *
     * @param courseId
     * @param studentOptional
     * @return boolean if the student was enrolled and a message
     */
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
        /**
         * Compare two specific courses by the total enrolled students in each one.
         *
         * @param courseDTO1
         * @param courseDTO2
         * @return An int value with -1 if the first course has more students, 1 if the second course has more students and 0 if they are equal
         */
        @Override
        public int compare(CourseDTO courseDTO1, CourseDTO courseDTO2) {
            if (courseDTO1.getTotalEnrollments() == courseDTO2.getTotalEnrollments()) {
                return 0;
            }
            return courseDTO1.getTotalEnrollments() > courseDTO2.getTotalEnrollments() ? -1 : 1;
        }
    }
}
