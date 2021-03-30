package sap.faculty_management_system.request;

import sap.faculty_management_system.model.enums.AcademicYear;

public class StudentRequest {
    private String name;
    private AcademicYear academicYear;

    public StudentRequest() {
    }

    public StudentRequest(String name, AcademicYear academicYear) {
        this.name = name;
        this.academicYear = academicYear;
    }

    public String getName() {
        return name;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    @Override
    public String toString() {
        return " StudentRequest{" +
                "name='" + name + '\'' +
                ", academicYear=" + academicYear +
                '}';
    }
}
