package edu.hogwarts.studentadmin.repository;

import edu.hogwarts.studentadmin.models.House;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, String> {
    House findByName(String houseName);
}

