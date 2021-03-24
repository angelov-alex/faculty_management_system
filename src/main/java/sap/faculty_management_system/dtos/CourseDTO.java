package sap.faculty_management_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sap.faculty_management_system.models.Credit;
import sap.faculty_management_system.models.Student;
import sap.faculty_management_system.models.Teacher;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private long id;

    private String name;

    private Teacher courseLeader;

    private Credit credit;

    private int totalEnrollments;

}
