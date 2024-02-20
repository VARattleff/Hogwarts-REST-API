package edu.hogwarts.studentadmin.controller;

import edu.hogwarts.studentadmin.dto.TeacherDTO;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repository.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAll(){
        var teachers = this.teacherRepository.findAll();
        if(!teachers.isEmpty()){
            List<TeacherDTO> teacherDTOs = teachers.stream()
                    .map(teacher -> {
                        String houseName = teacher.getHouse() != null ? teacher.getHouse().getName() : null;
                        return new TeacherDTO(
                                teacher.getFirstName(),
                                teacher.getMiddleName(),
                                teacher.getLastName(),
                                houseName,
                                teacher.isHeadOfHouse(),
                                teacher.getEmployment(),
                                teacher.getEmploymentStart(),
                                teacher.getEmploymentEnd());
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(teacherDTOs);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> get(@PathVariable Long id){
        var teacher = this.teacherRepository.findById(id);
        return teacher.map(t -> new TeacherDTO(
                        t.getFirstName(),
                        t.getMiddleName(),
                        t.getLastName(),
                        t.getHouse().getName(),
                        t.isHeadOfHouse(),
                        t.getEmployment(),
                        t.getEmploymentStart(),
                        t.getEmploymentEnd()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
    public ResponseEntity<TeacherDTO> delete(@PathVariable("id") long id){
        var teacherToDelete = this.teacherRepository.findById(id);
        if(teacherToDelete.isPresent()) {
            var teacherDTO = new TeacherDTO(
                    teacherToDelete.get().getFirstName(),
                    teacherToDelete.get().getMiddleName(),
                    teacherToDelete.get().getLastName(),
                    teacherToDelete.get().getHouse().getName(),
                    teacherToDelete.get().isHeadOfHouse(),
                    teacherToDelete.get().getEmployment(),
                    teacherToDelete.get().getEmploymentStart(),
                    teacherToDelete.get().getEmploymentEnd());
            this.teacherRepository.delete(teacherToDelete.get());
            return ResponseEntity.ok(teacherDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Teacher> partialUpdate(@RequestBody Teacher updatedTeacher, @PathVariable("id") Long id) {
        var teacherToUpdate = teacherRepository.findById(id);
        if(teacherToUpdate.isPresent()) {
            var existingTeacher = teacherToUpdate.get();

            if(updatedTeacher.getFirstName() != null) {
                existingTeacher.setFirstName(updatedTeacher.getFirstName());
            }
            if(updatedTeacher.getMiddleName() != null) {
                existingTeacher.setMiddleName(updatedTeacher.getMiddleName());
            }
            if(updatedTeacher.getLastName() != null) {
                existingTeacher.setLastName(updatedTeacher.getLastName());
            }
            if(updatedTeacher.getDateOfBirth() != null) {
                existingTeacher.setDateOfBirth(updatedTeacher.getDateOfBirth());
            }

            existingTeacher.setHeadOfHouse(updatedTeacher.isHeadOfHouse());

            if(updatedTeacher.getEmployment() != null) {
                existingTeacher.setEmployment(updatedTeacher.getEmployment());
            }
            if(updatedTeacher.getEmploymentStart() != null) {
                existingTeacher.setEmploymentStart(updatedTeacher.getEmploymentStart());
            }
            if(updatedTeacher.getEmploymentEnd() != null) {
                existingTeacher.setEmploymentEnd(updatedTeacher.getEmploymentEnd());
            }
            if(updatedTeacher.getHouse() != null) {
                existingTeacher.setHouse(updatedTeacher.getHouse());
            }

            teacherRepository.save(existingTeacher);
            return ResponseEntity.ok(existingTeacher);
        }
        return ResponseEntity.notFound().build();
    }

}
