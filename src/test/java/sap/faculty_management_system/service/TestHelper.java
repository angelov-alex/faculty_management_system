package sap.faculty_management_system.service;

import sap.faculty_management_system.model.Course;
import sap.faculty_management_system.model.Credit;
import sap.faculty_management_system.model.Student;
import sap.faculty_management_system.model.Teacher;
import sap.faculty_management_system.model.enums.AcademicYear;
import sap.faculty_management_system.model.enums.Rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestHelper {

    public static List<Teacher> createListOfOneTeacher(String name) {
        List<Teacher> teacherList = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setRank(Rank.ASSISTANT);
        teacherList.add(teacher);
        return teacherList;
    }

    public static List<Teacher> createListOfTwoTeachers() {
        Teacher teacher1 = new Teacher();
        teacher1.setId("a1");
        teacher1.setName("Name1");
        teacher1.setRank(Rank.DOCENT);
        teacher1.setLeadCourses(Collections.emptyList());

        Teacher teacher2 = new Teacher();
        teacher2.setId("a2");
        teacher2.setName("Name2");
        teacher2.setRank(Rank.PROFESSOR);
        teacher2.setLeadCourses(Collections.emptyList());

        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher1);
        teacherList.add(teacher2);
        return teacherList;
    }

    public static List<Student> createListOfOneStudent(String name) {
        List<Student> studentList = new ArrayList<>();
        Student student = new Student();
        student.setName(name);
        student.setAcademicYear(AcademicYear.FOUR);
        studentList.add(student);
        return studentList;
    }

    public static List<Student> createListOfTwoStudents() {
        Student student1 = new Student();
        student1.setId("a1");
        student1.setName("Name1");
        student1.setAcademicYear(AcademicYear.ONE);

        Student student2 = new Student();
        student2.setId("a2");
        student2.setName("Name2");
        student2.setAcademicYear(AcademicYear.TWO);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        return studentList;
    }

    public static List<Course> createListOfOneCourse(String name) {
        List<Course> courseList = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.setId("a1");
        teacher.setName("Name1");
        teacher.setRank(Rank.DOCENT);
        teacher.setLeadCourses(Collections.emptyList());

        Credit credit = new Credit();
        credit.setCreditHours(6.5);

        Course course = new Course();

        course.setName(name);
        course.setCourseLeader(teacher);
        course.setCredit(credit);

        courseList.add(course);
        return courseList;
    }

    public static List<Course> createListOfTwoCourse() {
        List<Course> courseList = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        teacher1.setId("a1");
        teacher1.setName("Name1");
        teacher1.setRank(Rank.DOCENT);
        teacher1.setLeadCourses(Collections.emptyList());

        Teacher teacher2 = new Teacher();
        teacher2.setId("a2");
        teacher2.setName("Name2");
        teacher2.setRank(Rank.PROFESSOR);
        teacher2.setLeadCourses(Collections.emptyList());

        Credit credit1 = new Credit();
        credit1.setCreditHours(6.5);

        Credit credit2 = new Credit();
        credit2.setCreditHours(3.0);

        Course course1 = new Course();
        course1.setName("Java");
        course1.setCourseLeader(teacher1);
        course1.setCredit(credit1);

        Course course2 = new Course();
        course2.setName("CSS");
        course2.setCourseLeader(teacher2);
        course2.setCredit(credit2);

        courseList.add(course1);
        courseList.add(course2);
        return courseList;
    }
}
