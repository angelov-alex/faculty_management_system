package sap.faculty_management_system.dto;

import sap.faculty_management_system.model.enums.Rank;

public class TeacherDTO2 {

    private String name;

    private Rank rank;

    private int totalNumOfStudents;

    public TeacherDTO2(String name, Rank rank, int totalNumOfStudents) {
        this.name = name;
        this.rank = rank;
        this.totalNumOfStudents = totalNumOfStudents;
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

    public int getTotalNumOfStudents() {
        return totalNumOfStudents;
    }

    public void setTotalNumOfStudents(int totalNumOfStudents) {
        this.totalNumOfStudents = totalNumOfStudents;
    }
}
