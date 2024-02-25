package edu.hogwarts.studentadmin.services;

import edu.hogwarts.studentadmin.dto.*;
import edu.hogwarts.studentadmin.models.Course;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;

    private final HouseService houseService;

    public CourseService(CourseRepository courseRepository, TeacherService teacherService, StudentService studentService, HouseService houseService) {
        this.courseRepository = courseRepository;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.houseService = houseService;
    }

    public List<CourseResponseDto> findAll(){
        return courseRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<CourseResponseDto> findById(Long id){
        return courseRepository.findById(id).map(this::toDto);
    }

    public Optional<TeacherResponseDto> getTeacherByCourseId(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            return Optional.empty();
        }

        Course course = courseOptional.get();
        Teacher teacher = course.getTeacher();

        if (teacher == null) {
            return Optional.empty();
        }

        return Optional.of(teacherService.toDto(teacher));
    }

    public List<StudentResponseDto> getStudentsByCourseId(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            return Collections.emptyList();
        }

        Course course = courseOptional.get();
        List<Student> students = course.getStudents();
        if (students == null || students.isEmpty()) {
            return Collections.emptyList();
        }

        return students.stream()
                .map(studentService::toDto)
                .collect(Collectors.toList());
    }

    public CourseResponseDto save(CourseRequestDto course){
        return toDto(courseRepository.save(toEntity(course)));
    }

    public Optional<CourseResponseDto> update(Long id, CourseRequestDto course){
        if (courseRepository.existsById(id)) {
            Course entity = toEntity(course);
            entity.setId(id);
            return Optional.of(toDto(courseRepository.save(entity)));
        }
        return Optional.empty();
    }

    public Optional<CourseResponseDto> updateStudentInCourse(Long courseId, Long studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            return Optional.empty();
        }

        Course course = courseOptional.get();
        StudentResponseDto studentDto = studentService.findById(studentId).orElse(null);
        if (studentDto == null) {
            return Optional.empty();
        }

        Student student = studentService.convertToRequestDto(studentDto);
        List<Student> students = course.getStudents();
        if (students == null) {
            students = new ArrayList<>();
        }

        if (!students.contains(student)) {
            students.add(student);
            course.setStudents(students);
            courseRepository.save(course);
        }

        return Optional.of(toDto(course));
    }




    public Optional<CourseResponseDto> deleteById(Long id){
        Optional<CourseResponseDto> existingCourse = findById(id);
        courseRepository.deleteById(id);
        return existingCourse;
    }

    public Optional<CourseResponseDto> deleteStudentInCourseById(Long courseId, Long studentId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            return Optional.empty();
        }

        Course course = courseOptional.get();
        List<Student> students = course.getStudents();
        if (students != null) {
            students.removeIf(student -> Objects.equals(student.getId(), studentId));
            courseRepository.save(course);
        }

        return Optional.of(toDto(course));
    }



    public CourseResponseDto toDto(Course entity){
        CourseResponseDto dto = new CourseResponseDto();
        dto.setId(entity.getId());
        dto.setSubject(entity.getSubject());
        dto.setCurrent(entity.isCurrent());
        dto.setSchoolYear(entity.getSchoolYear());
        dto.setStudents(entity.getStudents());
        dto.setTeacher(entity.getTeacher());

        return dto;
    }

    public Course toEntity(CourseRequestDto dto){
        Course entity = new Course();
        entity.setId(dto.getId());
        entity.setStudents(dto.getStudents());
        entity.setCurrent(dto.getCurrent());
        entity.setSchoolYear(dto.getSchoolYear());
        entity.setSubject(dto.getSubject());
        entity.setTeacher(dto.getTeacher());

        return entity;
    }

    public void updateEntity(Course entity, CourseRequestDto dto){
        if (dto.getCurrent() != null) entity.setCurrent(dto.getCurrent());
        if (dto.getStudents() != null) entity.setStudents(dto.getStudents());
        if (dto.getSchoolYear() != null) entity.setSchoolYear(dto.getSchoolYear());
        if (dto.getTeacher() != null) entity.setTeacher(dto.getTeacher());
        if (dto.getSubject() != null) entity.setSubject(dto.getSubject());
    }
}
