package edu.hogwarts.studentadmin.controller;

import edu.hogwarts.studentadmin.dto.CoursesDTO;
import edu.hogwarts.studentadmin.models.Course;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repository.CourseRepository;
import edu.hogwarts.studentadmin.repository.StudentRepository;
import edu.hogwarts.studentadmin.repository.TeacherRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public CourseController(CourseRepository courseRepository, TeacherRepository teacherRepository, StudentRepository studentRepository ) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
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
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

    @GetMapping("/{id}/teacher")
    public ResponseEntity<Teacher> getTeacher(@PathVariable long id) {
        var courseOptional = this.courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseOptional.get();
        Teacher teacher = course.getTeacher();

        if (teacher == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(teacher);
    }


    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudents(@PathVariable long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseOptional.get();
        List<Student> students = course.getStudents();

        if (students == null || students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}/teacher/{teacherId}")
    public ResponseEntity<Course> setTeacherForCourse(@PathVariable long id, @PathVariable long teacherId) {

        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if (teacher == null) {
            return ResponseEntity.notFound().build();
        }

        course.setTeacher(teacher);
        courseRepository.save(course);

        return ResponseEntity.ok(course);
    }


    @PutMapping("/{id}/students/{studentId}")
    public ResponseEntity<Course> addStudentToCourse(@PathVariable long id, @PathVariable long studentId) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        if (!student.getSchoolYear().equals(course.getSchoolYear())) {
            return ResponseEntity.badRequest().body(course);
        }

        List<Student> students = course.getStudents();
        if (students == null) {
            students = new ArrayList<>();
        }

        students.add(student);
        course.setStudents(students);
        courseRepository.save(course);

        return ResponseEntity.ok(course);
    }



    @DeleteMapping("/{id}/teacher")
    public ResponseEntity<Course> removeTeacherFromCourse(@PathVariable long id) {

        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        course.setTeacher(null);
        courseRepository.save(course);

        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{id}/students/{studentId}")
    public ResponseEntity<Course> removeStudentFromCourse(@PathVariable long id, @PathVariable long studentId) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        List<Student> students = course.getStudents();
        if (students != null) {
            students.removeIf(student -> student.getId() == studentId);
        }

        course.setStudents(students);
        courseRepository.save(course);

        return ResponseEntity.ok(course);
    }

    @PostMapping("/{id}/students")
    public ResponseEntity<Course> addStudentsToCourse(@PathVariable long id, @RequestBody CoursesDTO coursesDTO) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        List<Student> students = course.getStudents();
        if (students == null) {
            students = new ArrayList<>();
        }

        List<Object> studentsData = coursesDTO.getStudentsData();

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
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> patch(@RequestBody Map<String, Object> updates, @PathVariable("id") Long id) {
        var courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course courseToUpdate = courseOptional.get();

            if (updates.containsKey("teacherId")) {
                Long teacherId = Long.parseLong(updates.get("teacherId").toString());
                Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);

                if (teacherOptional.isPresent()) {
                    Teacher teacher = teacherOptional.get();
                    courseToUpdate.setTeacher(teacher);
                } else {
                    return ResponseEntity.badRequest().body("Teacher with id " + teacherId + " not found");
                }
            }

            courseRepository.save(courseToUpdate);
            return ResponseEntity.ok(courseToUpdate);
        }
        return ResponseEntity.notFound().build();
    }
}