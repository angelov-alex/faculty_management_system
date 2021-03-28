package sap.faculty_management_system.mockobjects.services;

import sap.faculty_management_system.model.Teacher;
import sap.faculty_management_system.model.enums.Rank;

import java.util.ArrayList;
import java.util.List;

public class TestHelper {
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

    public static List<Teacher> createListOfOneTeacher(String name) {
        List<Teacher> teacherList = new ArrayList<>();
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setRank(Rank.ASSISTANT);
        teacherList.add(teacher);
        return teacherList;
    }
}
