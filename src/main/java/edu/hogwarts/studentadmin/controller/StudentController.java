package edu.hogwarts.studentadmin.controller;
import edu.hogwarts.studentadmin.dto.StudentDTO;

import edu.hogwarts.studentadmin.models.SchoolYear;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;


    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAll() {
        var students = this.studentRepository.findAll();
        if (!students.isEmpty()) {
            List<StudentDTO> studentDTOs = students.stream()
                    .map(student -> new StudentDTO(
                            student.getFirstName(),
                            student.getMiddleName(),
                            student.getLastName(),
                            student.getHouse().getName()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(studentDTOs);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> get(@PathVariable Long id) {
        var student = this.studentRepository.findById(id);
        return student.map(value -> ResponseEntity.ok(new StudentDTO(
                        value.getFirstName(),
                        value.getMiddleName(),
                        value.getLastName(),
                        value.getHouse().getName())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Student student) {
        if (student.getFirstName() == null) {
            return ResponseEntity.badRequest().body("Name is required.");
        }

        String fullName = student.getFirstName();
        String[] nameParts = fullName.split(" ");
        String firstName = nameParts[0];
        String middleName = null;
        if (nameParts.length > 2) {
            StringBuilder middleNameBuilder = new StringBuilder();
            for (int i = 1; i < nameParts.length - 1; i++) {
                middleNameBuilder.append(nameParts[i]).append(" ");
            }
            middleName = middleNameBuilder.toString().trim();
        }

        String lastName = nameParts[nameParts.length - 1];

        student.setFirstName(firstName);
        student.setMiddleName(middleName);
        student.setLastName(lastName);

        Student savedStudent = studentRepository.save(student);

        return ResponseEntity.ok(savedStudent);
    }



    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@RequestBody Student student, @PathVariable("id") Long id) {
        var studentToUpdate = studentRepository.findById(id);
        if (studentToUpdate.isPresent()) {
            Student updatedStudent = studentToUpdate.get();
            updatedStudent.setFirstName(student.getFirstName());
            updatedStudent.setMiddleName(student.getMiddleName());
            updatedStudent.setLastName(student.getLastName());
            updatedStudent.setDateOfBirth(student.getDateOfBirth());
            updatedStudent.setPrefect(student.isPrefect());
            updatedStudent.setEnrollmentYear(student.getEnrollmentYear());
            updatedStudent.setGraduationYear(student.getGraduationYear());
            updatedStudent.setGraduated(student.isGraduated());
            updatedStudent.setHouse(student.getHouse());
            studentRepository.save(updatedStudent);
            return ResponseEntity.ok(new StudentDTO(
                    updatedStudent.getFirstName(),
                    updatedStudent.getMiddleName(),
                    updatedStudent.getLastName(),
                    updatedStudent.getHouse().getName()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> delete(@PathVariable("id") Long id) {
        var studentToDelete = this.studentRepository.findById(id);
        if (studentToDelete.isPresent()) {
            this.studentRepository.delete(studentToDelete.get());
            Student deletedStudent = studentToDelete.get();
            return ResponseEntity.ok(new StudentDTO(
                    deletedStudent.getFirstName(),
                    deletedStudent.getMiddleName(),
                    deletedStudent.getLastName(),
                    deletedStudent.getHouse().getName()));
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
        var studentToUpdate = studentRepository.findById(id);
        if (studentToUpdate.isPresent()) {
            Student updatedStudent = studentToUpdate.get();

            if (updates.containsKey("prefect")) {
                updatedStudent.setPrefect((boolean) updates.get("prefect"));
            }
            if (updates.containsKey("schoolYear")) {
                String schoolYearName = (String) updates.get("schoolYear");
                SchoolYear schoolYear = SchoolYear.valueOf(schoolYearName);
                updatedStudent.setSchoolYear(schoolYear);
            }

            if (updates.containsKey("graduationYear")) {
                int graduationYear = (int) updates.get("graduationYear");
                updatedStudent.setGraduationYear(graduationYear);
                updatedStudent.setGraduated(true);
            }

            studentRepository.save(updatedStudent);
            return ResponseEntity.ok(updatedStudent);
        }
        return ResponseEntity.notFound().build();
    }


}
