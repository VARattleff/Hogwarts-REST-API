package edu.hogwarts.studentadmin.controllers;

import edu.hogwarts.studentadmin.dto.TeacherRequestDto;
import edu.hogwarts.studentadmin.dto.TeacherResponseDto;
import edu.hogwarts.studentadmin.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherServices;

    public TeacherController(TeacherService teacherServices) {
        this.teacherServices = teacherServices;
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAll() {
        var teacher = this.teacherServices.findAll();
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> get(@PathVariable Long id) {
        var teacher = this.teacherServices.findById(id);
        return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDto> create(@RequestBody TeacherRequestDto teacher) {
        TeacherResponseDto savedTeacher = teacherServices.save(teacher);
        return ResponseEntity.ok(savedTeacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> update(@RequestBody TeacherRequestDto teacher, @PathVariable("id") Long id) {
        return ResponseEntity.of(teacherServices.updateIfExist(id, teacher));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> patch(@RequestBody TeacherRequestDto updatedTeacher, @PathVariable("id") Long id) {
        Optional<TeacherResponseDto> patchedTeacher = teacherServices.patchStudent(id, updatedTeacher);
        return patchedTeacher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.of(teacherServices.deleteById(id));
    }
}
