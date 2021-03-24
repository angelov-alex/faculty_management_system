package sap.faculty_management_system.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sap.faculty_management_system.models.Credit;

@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private long id;

    private String name;

    private Credit credit;

    private int totalEnrollments;

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

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public int getTotalEnrollments() {
        return totalEnrollments;
    }

    public void setTotalEnrollments(int totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }
}
