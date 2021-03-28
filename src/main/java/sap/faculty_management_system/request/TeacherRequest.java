package sap.faculty_management_system.request;

import sap.faculty_management_system.model.enums.Rank;

public class TeacherRequest {
    private String name;
    private Rank rank;

    public String getName() {
        return name;
    }

    public Rank getRank() {
        return rank;
    }
}
