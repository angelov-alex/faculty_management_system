package sap.faculty_management_system.util;

import org.springframework.util.ObjectUtils;
import sap.faculty_management_system.dtos.CourseDTO;
import sap.faculty_management_system.dtos.StudentDTO;
import sap.faculty_management_system.dtos.TeacherDTO;
import sap.faculty_management_system.models.Course;
import sap.faculty_management_system.models.Student;
import sap.faculty_management_system.models.Teacher;

import java.util.Set;
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
        teacherDTO.setLeadCourses(teacher.getLeadCourses().stream().map(a -> convertCourseToDTO(a)).collect(Collectors.toSet()));
        teacherDTO.setRank(teacher.getRank());
        teacherDTO.setTotalNumOfLeadCourses(teacher.getLeadCourses().size());

        Set<Course> leadCourses = teacher.getLeadCourses();
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
        studentDTO.setSemester(student.getSemester().getValue());
        studentDTO.setEnrollments(student.getEnrollments().stream().map(c -> convertCourseToDTO(c)).collect(Collectors.toSet()));

        Set<Course> enrollments = student.getEnrollments();
        double totalCredits = 0.0;

        for (Course course : enrollments) {
            totalCredits += course.getCredit().getCreditHours();
        }
        studentDTO.setSumOfCredit(totalCredits);

        return studentDTO;
    }
}
