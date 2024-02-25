package edu.hogwarts.studentadmin.dto;

import java.util.List;

public class CourseDto {
    private List<Object> studentsData;

    public List<Object> getStudentsData() {
        return studentsData;
    }

    public void setStudentsData(List<Object> studentsData) {
        this.studentsData = studentsData;
    }

}
