package sap.faculty_management_system.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sap.faculty_management_system.models.enums.Rank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
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
}
