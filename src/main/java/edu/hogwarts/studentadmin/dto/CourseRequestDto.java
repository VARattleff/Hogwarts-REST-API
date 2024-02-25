package edu.hogwarts.studentadmin.dto;

import edu.hogwarts.studentadmin.models.SchoolYear;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.models.Teacher;

import java.util.ArrayList;
import java.util.List;

public class CourseRequestDto {
    private Long id;
    private String subject;
    private SchoolYear schoolYear;
    private Boolean current;
    private Teacher teacher;
    private final List<Student> students = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public Boolean getCurrent() {
        return current;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Student> getStudents() {
        return students;
    }
}
