package edu.hogwarts.studentadmin.controllers;

import edu.hogwarts.studentadmin.dto.StudentRequestDto;
import edu.hogwarts.studentadmin.dto.StudentResponseDto;
import edu.hogwarts.studentadmin.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentServices;

    public StudentController(StudentService studentServices) {
        this.studentServices = studentServices;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAll() {
        var students = this.studentServices.findAll();
        if (students != null) {
            return ResponseEntity.ok(students);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> get(@PathVariable Long id) {
        var student = this.studentServices.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<StudentResponseDto> create(@RequestBody StudentRequestDto student) {
        StudentResponseDto savedStudent = studentServices.save(student);
        return ResponseEntity.ok(savedStudent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDto> patch(@RequestBody StudentRequestDto updatedStudent, @PathVariable("id") Long id) {
        Optional<StudentResponseDto> patchedStudent = studentServices.patchStudent(id, updatedStudent);
        return patchedStudent.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> update(@RequestBody StudentRequestDto student, @PathVariable("id") Long id) {
        return ResponseEntity.of(studentServices.updateIfExist(id, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponseDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.of(studentServices.deleteById(id));
    }
}
