package edu.hogwarts.studentadmin.controller;

import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repository.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAll(){
        var teacher = this.teacherRepository.findAll();
        if(!teacher.isEmpty()){
            return ResponseEntity.ok(teacher);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> get(@PathVariable Long id){
        var teacher = this.teacherRepository.findById(id);
        return teacher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Teacher teacher){
        if(teacher.getFirstName() == null) {
            return ResponseEntity.badRequest().body("First name is required.");
        }
        return ResponseEntity.ok(teacherRepository.save(teacher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> update(@RequestBody Teacher teacher, @PathVariable("id") Long id) {
        var teacherToUpdate = teacherRepository.findById(id);
        if(teacherToUpdate.isPresent()) {
            var updatedTeacher = teacherToUpdate.get();
            updatedTeacher.setFirstName(teacher.getFirstName());
            updatedTeacher.setMiddleName(teacher.getMiddleName());
            updatedTeacher.setLastName(teacher.getLastName());
            updatedTeacher.setDateOfBirth(teacher.getDateOfBirth());
            updatedTeacher.setHeadOfHouse(teacher.isHeadOfHouse());
            updatedTeacher.setEmployment(teacher.getEmployment());
            updatedTeacher.setEmploymentStart(teacher.getEmploymentStart());
            updatedTeacher.setEmploymentEnd(teacher.getEmploymentEnd());
            updatedTeacher.setHouse(teacher.getHouse());
            teacherRepository.save(updatedTeacher);
            return ResponseEntity.ok(updatedTeacher);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> delete(@PathVariable("id") long id){
        var teacherToDelete = this.teacherRepository.findById(id);
        if(teacherToDelete.isPresent()) {
            this.teacherRepository.delete(teacherToDelete.get());
            return ResponseEntity.ok(teacherToDelete.get());
        }
        return ResponseEntity.notFound().build();
    }

}


