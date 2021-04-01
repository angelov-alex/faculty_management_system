package sap.facultymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sap.facultymanagementsystem.model.Credit;

@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private String id;

    private String name;

    private Credit credit;

    private int totalEnrollments;

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
