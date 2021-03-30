package sap.faculty_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sap.faculty_management_system.model.User;
import sap.faculty_management_system.model.enums.Rank;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO implements User {

    private String id;

    private String name;

    private Rank rank;

    private Set<CourseDTO> leadCourses;

    private int totalNumOfLeadCourses;

    private int totalNumOfStudents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Set<CourseDTO> getLeadCourses() {
        return leadCourses;
    }

    public void setLeadCourses(Set<CourseDTO> leadCourses) {
        this.leadCourses = leadCourses;
    }

    public int getTotalNumOfLeadCourses() {
        return totalNumOfLeadCourses;
    }

    public void setTotalNumOfLeadCourses(int totalNumOfLeadCourses) {
        this.totalNumOfLeadCourses = totalNumOfLeadCourses;
    }

    public int getTotalNumOfStudents() {
        return totalNumOfStudents;
    }

    public void setTotalNumOfStudents(int totalNumOfStudents) {
        this.totalNumOfStudents = totalNumOfStudents;
    }
}
