package edu.hogwarts.studentadmin.modals;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Map;

@Entity(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subject;
    private int schoolYear;
    private  boolean current;
    private Teacher teacher;
    private Student[] students;
}
