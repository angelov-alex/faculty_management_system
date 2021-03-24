package sap.faculty_management_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sap.faculty_management_system.models.Course;
import sap.faculty_management_system.models.enums.Rank;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    private long id;

    private String name;

    private Rank rank;

    private Set<Course> leadCourses;

    private int totalNumOfLeadCourses;

    private int totalNumOfStudents;
}
