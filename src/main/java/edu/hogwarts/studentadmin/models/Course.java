package edu.hogwarts.studentadmin.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subject;
    @Enumerated(EnumType.STRING)
    private SchoolYear schoolYear;
    private boolean current;
    @ManyToOne(fetch = FetchType.EAGER)
    private Teacher teacher;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();

    public Course(){}

    public Course(String subject, SchoolYear schoolYear, boolean current, Teacher teacher, List<Student> students) {
        this.subject = subject;
        this.schoolYear = schoolYear;
        this.current = current;
        this.teacher = teacher;
        this.students = students;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", schoolYear=" + schoolYear +
                ", current=" + current +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }
}
