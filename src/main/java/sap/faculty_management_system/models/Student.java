package sap.faculty_management_system.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sap.faculty_management_system.models.enums.Semester;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {
    private Semester semester;
    private List<Course> enrollments;

}
