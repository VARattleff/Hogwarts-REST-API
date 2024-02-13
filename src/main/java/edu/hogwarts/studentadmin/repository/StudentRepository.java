package edu.hogwarts.studentadmin.repository;

import edu.hogwarts.studentadmin.modals.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
