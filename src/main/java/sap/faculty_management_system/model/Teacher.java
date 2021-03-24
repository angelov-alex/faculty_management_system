package sap.faculty_management_system.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sap.faculty_management_system.model.enums.Rank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TEACHER")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "RANK")
    @Enumerated(EnumType.STRING)
    private Rank rank;

    @OneToMany(cascade = CascadeType.ALL)

    @JoinColumn(name = "TEACHER_ID")
    private Set<Course> leadCourses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Set<Course> getLeadCourses() {
        return leadCourses;
    }

    public void setLeadCourses(Set<Course> leadCourses) {
        this.leadCourses = leadCourses;
    }
}
