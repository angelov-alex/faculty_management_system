package sap.faculty_management_system.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sap.faculty_management_system.model.enums.Rank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TEACHER", schema = "SAP")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "RANK")
    @Enumerated(EnumType.STRING)
    private Rank rank;

    @OneToMany(cascade = CascadeType.ALL)

    @JoinColumn(name = "TEACHER_ID")
    private List<Course> leadCourses = new ArrayList<>();

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

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public List<Course> getLeadCourses() {
        return leadCourses;
    }

    public void setLeadCourses(List<Course> leadCourses) {
        this.leadCourses = leadCourses;
    }
}
