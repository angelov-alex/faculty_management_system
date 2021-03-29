package sap.faculty_management_system.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import sap.faculty_management_system.model.enums.AcademicYear;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "STUDENT", schema = "SAP")
public class Student {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "UUID", unique = true)
    private String id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "ACADEMIC_YEAR")
    @Enumerated(EnumType.STRING)
    private AcademicYear academicYear;

    @ManyToMany
    @JoinTable(
            name = "STUDENT_COURSE", schema = "SAP",
            joinColumns = @JoinColumn(name = "STUDENT_UUID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_UUID"))
    private List<Course> enrollments = new ArrayList<>();

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

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public List<Course> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Course> enrollments) {
        this.enrollments = enrollments;
    }

    public void addCourse(Course course) {
        this.enrollments.add(course);
    }

    public void removeCourse(Course course) {
        this.enrollments.remove(course);
    }

}
