package sap.faculty_management_system.mockobjects.services;

import sap.faculty_management_system.model.Student;
import sap.faculty_management_system.model.Teacher;
import sap.faculty_management_system.model.enums.AcademicYear;
import sap.faculty_management_system.model.enums.Rank;

import java.util.ArrayList;
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

        Teacher teacher2 = new Teacher();
        teacher2.setId("a2");
        teacher2.setName("Name2");
        teacher2.setRank(Rank.PROFESSOR);

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
}
