package sap.faculty_management_system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COURSE", schema = "SAP")
public class Course {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "UUID", unique = true)
    private String id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "TEACHER_UUID")
    private Teacher courseLeader;

    @ManyToOne
    @JoinColumn(name = "CREDIT_UUID")
    private Credit credit;

    @ManyToMany
    @JoinTable(
            name = "STUDENT_COURSE", schema = "SAP",
            joinColumns = @JoinColumn(name = "COURSE_UUID"),
            inverseJoinColumns = @JoinColumn(name = "STUDENT_UUID"))
    private List<Student> enrolledStudents = new ArrayList<>();

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

    public Teacher getCourseLeader() {
        return courseLeader;
    }

    public void setCourseLeader(Teacher courseLeader) {
        this.courseLeader = courseLeader;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
