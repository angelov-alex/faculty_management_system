package sap.facultymanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import sap.facultymanagementsystem.model.enums.Rank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TEACHER", schema = "SAP")
public class Teacher implements User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "UUID", unique = true)
    private String id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "RANK")
    @Enumerated(EnumType.STRING)
    private Rank rank;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "TEACHER_UUID")
    private List<Course> leadCourses = new ArrayList<>();

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

    public List<Course> getLeadCourses() {
        return leadCourses;
    }

    public void setLeadCourses(List<Course> leadCourses) {
        this.leadCourses = leadCourses;
    }
}
