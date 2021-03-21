package sap.faculty_management_system.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sap.faculty_management_system.models.enums.Rank;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User{
    private Rank rank;
    private List<Course> leadCourses;
}
