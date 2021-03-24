package sap.faculty_management_system.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import sap.faculty_management_system.model.enums.Semester;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SEMESTER")
    @Enumerated(EnumType.STRING)
    private Semester semester;

    @ManyToMany
    @JoinTable(
            name = "STUDENT_COURSE",
            joinColumns = @JoinColumn(name = "STUDENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
    private Set<Course> enrollments;

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

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Set<Course> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Course> enrollments) {
        this.enrollments = enrollments;
    }
}
