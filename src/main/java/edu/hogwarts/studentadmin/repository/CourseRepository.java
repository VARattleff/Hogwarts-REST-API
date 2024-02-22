package edu.hogwarts.studentadmin.repository;

import edu.hogwarts.studentadmin.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByStudentsId(long id);
}
