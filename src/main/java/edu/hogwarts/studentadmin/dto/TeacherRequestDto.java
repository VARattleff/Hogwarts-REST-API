package edu.hogwarts.studentadmin.dto;

import edu.hogwarts.studentadmin.models.EmpType;

import java.time.LocalDate;

public record TeacherRequestDto(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        String name,
        LocalDate dateOfBirth,
        String house,
        Boolean headOfHouse,
        EmpType employment,
        LocalDate employmentStart,
        LocalDate employmentEnd
) {
    public TeacherRequestDto{
        if (name != null){
            String[] parts = name.split(" ");
            firstName = parts[0];
            lastName = parts[parts.length -1];
            if (parts.length > 2) {
                middleName = parts[1];
            }
        }
    }
}
