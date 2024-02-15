package edu.hogwarts.studentadmin.controller;

import edu.hogwarts.studentadmin.models.Course;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repository.CourseRepository;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        var course = this.courseRepository.findAll();
        if (!course.isEmpty()) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> get(@PathVariable long id) {
        var course = this.courseRepository.findById(id);
        if(course.isPresent()) {
            return ResponseEntity.ok(course.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Course course){
        if(course.getSubject() == null){
            return ResponseEntity.badRequest().body("Subject is required");
        }
        return ResponseEntity.ok(courseRepository.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@RequestBody Course course, @PathVariable("id") Long id) {
        var courseToUpdated = courseRepository.findById(id);
        if (courseToUpdated.isPresent()) {
            var updatedCourse = courseToUpdated.get();
            updatedCourse.setSubject(course.getSubject());
            updatedCourse.setSchoolYear(course.getSchoolYear());
            updatedCourse.setCurrent(course.isCurrent());
            courseRepository.save(updatedCourse);
            return ResponseEntity.ok(updatedCourse);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> delete(@PathVariable("id") Long id) {
        var courseToDelete = this.courseRepository.findById(id);
        if (courseToDelete.isPresent()) {
            this.courseRepository.delete(courseToDelete.get());
            return ResponseEntity.ok(courseToDelete.get());
        }
        return ResponseEntity.notFound().build();
    }
}


