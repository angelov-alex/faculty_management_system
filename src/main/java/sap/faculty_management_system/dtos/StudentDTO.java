package sap.faculty_management_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sap.faculty_management_system.models.Course;
import sap.faculty_management_system.models.enums.Semester;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private long id;

    private String name;

    private Semester semester;

    private Set<Course> enrollments;

    private double sumOfCredit;

}
