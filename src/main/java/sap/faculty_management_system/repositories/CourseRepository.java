package sap.faculty_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sap.faculty_management_system.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
