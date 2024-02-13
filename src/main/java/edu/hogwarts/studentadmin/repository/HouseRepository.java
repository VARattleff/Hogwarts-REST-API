package edu.hogwarts.studentadmin.repository;

import edu.hogwarts.studentadmin.modals.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}