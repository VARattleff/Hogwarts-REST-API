package edu.hogwarts.studentadmin.controllers;

import edu.hogwarts.studentadmin.dto.*;
import edu.hogwarts.studentadmin.models.Course;
import edu.hogwarts.studentadmin.services.CourseService;
import edu.hogwarts.studentadmin.services.StudentService;
import edu.hogwarts.studentadmin.services.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;

    private final StudentService studentService;

    public CourseController(CourseService courseService, TeacherService teacherService, StudentService studentService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAll() {
        var course = this.courseService.findAll();
        if (!course.isEmpty()) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> get(@PathVariable long id) {
        var course = this.courseService.findById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourseResponseDto> create(@RequestBody CourseRequestDto course){
        if(course.getSubject() == null){
            return null;
        }
        return ResponseEntity.ok(courseService.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> update(@RequestBody CourseRequestDto course, @PathVariable("id") Long id) {
        return ResponseEntity.of(courseService.update(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CourseResponseDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.of(courseService.deleteById(id));
    }

    @GetMapping("/{id}/teacher")
    public ResponseEntity<TeacherResponseDto> getTeacher(@PathVariable long id) {
        Optional<TeacherResponseDto> teacherOptional = courseService.getTeacherByCourseId(id);
        if (teacherOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TeacherResponseDto teacher = teacherOptional.get();
        return ResponseEntity.ok(teacher);
    }


    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByCourseId(@PathVariable Long id) {
        List<StudentResponseDto> students = courseService.getStudentsByCourseId(id);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

/*    @PostMapping("/{id}/students")
    public ResponseEntity<Course> addStudentsToCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        List<Student> students = course.getStudents();
        if (students == null) {
            students = new ArrayList<>();
        }

        List<Object> studentsData = courseDto.getStudentsData();
        if (studentsData != null) {
            for (Object data : studentsData) {
                if (data instanceof Map) {
                    Map<String, Object> studentMap = (Map<String, Object>) data;
                    if (studentMap.containsKey("id")) {
                        long studentId = ((Number) studentMap.get("id")).longValue();
                        Student student = studentRepository.findById(studentId).orElse(null);
                        if (student != null && !students.contains(student)) {
                            students.add(student);
                        }
                    } else if (studentMap.containsKey("name")) {
                        String studentName = (String) studentMap.get("name");
                        Student student = studentRepository.findByFirstName(studentName);
                        if (student == null) {
                            student = new Student(studentName);
                            studentRepository.save(student);
                        }
                        if (!students.contains(student)) {
                            students.add(student);
                        }
                    }
                }
            }
        }

        course.setStudents(students);
        courseRepository.save(course);

        return ResponseEntity.ok(course);
    }*/


/*
@PatchMapping("/{id}/teacher/{teacherId}")
public ResponseEntity<Course> patchTeacherForCourse(@PathVariable long id, @PathVariable long teacherId, @RequestBody Teacher teacherToPatch){
    Course course = courseRepository.findById(id).orElse(null);
    if (course == null) {
        return ResponseEntity.notFound().build();
    }
    var teacherToUpdate = teacherRepository.findById(teacherId);
    if(teacherToUpdate.isPresent()) {
        var existingTeacher = teacherToUpdate.get();

        if(teacherToPatch.getFirstName() != null) {
            existingTeacher.setFirstName(teacherToPatch.getFirstName());
        }
        if(teacherToPatch.getMiddleName() != null) {
            existingTeacher.setMiddleName(teacherToPatch.getMiddleName());
        }
        if(teacherToPatch.getLastName() != null) {
            existingTeacher.setLastName(teacherToPatch.getLastName());
        }
        if(teacherToPatch.getDateOfBirth() != null) {
            existingTeacher.setDateOfBirth(teacherToPatch.getDateOfBirth());
        }

        existingTeacher.setHeadOfHouse(teacherToPatch.isHeadOfHouse());

        if(teacherToPatch.getEmployment() != null) {
            existingTeacher.setEmployment(teacherToPatch.getEmployment());
        }
        if(teacherToPatch.getEmploymentStart() != null) {
            existingTeacher.setEmploymentStart(teacherToPatch.getEmploymentStart());
        }
        if(teacherToPatch.getEmploymentEnd() != null) {
            existingTeacher.setEmploymentEnd(teacherToPatch.getEmploymentEnd());
        }
        if(teacherToPatch.getHouse() != null) {
            existingTeacher.setHouse(teacherToPatch.getHouse());
        }

        teacherRepository.save(existingTeacher);

        course.setTeacher(existingTeacher);
        courseRepository.save(course);

        return ResponseEntity.ok(course);
    }
    return ResponseEntity.notFound().build();
}
*/

    @PutMapping("/{id}/students/{studentId}")
    public ResponseEntity<CourseResponseDto> addStudentToCourse(@PathVariable long id, @PathVariable long studentId) {
        Optional<CourseResponseDto> updatedCourse = courseService.updateStudentInCourse(id, studentId);
        return updatedCourse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}/students/{studentId}")
    public ResponseEntity<CourseResponseDto> removeStudentFromCourse(@PathVariable long id, @PathVariable long studentId) {
        Optional<CourseResponseDto> courseOptional = courseService.deleteStudentInCourseById(id, studentId);
        return courseOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
