package edu.hogwarts.studentadmin.models;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String subject;
    private int schoolYear;
    private boolean current;
    @OneToOne
    private Teacher teacher;
    @ManyToOne
    private Student[] students;

    public Course(){}

    public Course(long id, String subject, int schoolYear, boolean current, Teacher teacher, Student[] students) {
        this.id = id;
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

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
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

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
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
                ", students=" + Arrays.toString(students) +
                '}';
    }
}
