package sap.faculty_management_system.util;

import org.springframework.util.ObjectUtils;
import sap.faculty_management_system.dto.CourseDTO;
import sap.faculty_management_system.dto.StudentDTO;
import sap.faculty_management_system.dto.TeacherDTO;
import sap.faculty_management_system.model.Course;
import sap.faculty_management_system.model.Student;
import sap.faculty_management_system.model.Teacher;

import java.util.List;
import java.util.stream.Collectors;

public class DTOConverter {

    public static CourseDTO convertCourseToDTO(Course course) {
        if (ObjectUtils.isEmpty(course)) {
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setCredit(course.getCredit());
        courseDTO.setName(course.getName());
        courseDTO.setId(course.getId());
        courseDTO.setTotalEnrollments(course.getEnrolledStudents().size());

        return courseDTO;
    }

    public static TeacherDTO convertTeacherToDTO(Teacher teacher) {
        if (ObjectUtils.isEmpty(teacher)) {
            return null;
        }

        TeacherDTO teacherDTO = new TeacherDTO();

        teacherDTO.setName(teacher.getName());
        teacherDTO.setId(teacher.getId());
        teacherDTO.setLeadCourses(teacher.getLeadCourses().stream().map(DTOConverter::convertCourseToDTO).collect(Collectors.toSet()));
        teacherDTO.setRank(teacher.getRank());
        teacherDTO.setTotalNumOfLeadCourses(teacher.getLeadCourses().size());

        List<Course> leadCourses = teacher.getLeadCourses();
        int totalStudents = 0;

        for (Course course : leadCourses) {
            totalStudents += course.getEnrolledStudents().size();
        }
        teacherDTO.setTotalNumOfStudents(totalStudents);

        return teacherDTO;
    }

    public static StudentDTO convertStudentToDTO(Student student) {
        if (ObjectUtils.isEmpty(student)) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setName(student.getName());
        studentDTO.setId(student.getId());
        studentDTO.setAcademicYear(student.getAcademicYear().getValue());
        studentDTO.setEnrollments(student.getEnrollments().stream().map(DTOConverter::convertCourseToDTO).collect(Collectors.toSet()));

        List<Course> enrollments = student.getEnrollments();
        double totalCredits = 0.0;

        for (Course course : enrollments) {
            totalCredits += course.getCredit().getCreditHours();
        }
        studentDTO.setSumOfCredit(totalCredits);

        return studentDTO;
    }
}
