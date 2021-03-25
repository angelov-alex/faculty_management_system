package sap.faculty_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private long id;

    private String name;

    private int academicYear;

    private Set<CourseDTO> enrollments;

    private double sumOfCredit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public Set<CourseDTO> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<CourseDTO> enrollments) {
        this.enrollments = enrollments;
    }

    public double getSumOfCredit() {
        return sumOfCredit;
    }

    public void setSumOfCredit(double sumOfCredit) {
        this.sumOfCredit = sumOfCredit;
    }
}
