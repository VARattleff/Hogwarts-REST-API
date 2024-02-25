package edu.hogwarts.studentadmin.dto;

import edu.hogwarts.studentadmin.models.SchoolYear;

import java.time.LocalDate;

public record StudentRequestDto(
     Long id,
     String firstName,
     String middleName,
     String lastName,
     String name,
     LocalDate dateOfBirth,
     Boolean prefect,
     Integer enrollmentYear,
     Integer graduationYear,
     Boolean graduated,
     SchoolYear schoolYear,

     String house) {

    public StudentRequestDto{
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


