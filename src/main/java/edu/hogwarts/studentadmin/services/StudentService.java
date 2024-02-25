package edu.hogwarts.studentadmin.services;

import edu.hogwarts.studentadmin.dto.StudentRequestDto;
import edu.hogwarts.studentadmin.dto.StudentResponseDto;
import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repository.HouseRepository;
import edu.hogwarts.studentadmin.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final HouseRepository houseRepository;

    public StudentService(StudentRepository studentRepository, HouseRepository houseRepository) {
        this.studentRepository = studentRepository;
        this.houseRepository = houseRepository;
    }

    public List<StudentResponseDto> findAll() {
        return studentRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<StudentResponseDto> findById(Long id) {
        return studentRepository.findById(id).map(this::toDto);
    }


    public StudentResponseDto save(StudentRequestDto student) {
        return toDto(studentRepository.save(toEntity(student)));
    }



    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public Optional<StudentResponseDto> deleteById(Long id) {
        Optional<StudentResponseDto> existingStudent = findById(id);
        studentRepository.deleteById(id);
        return existingStudent;
    }

    public Optional<StudentResponseDto> updateIfExist(Long id, StudentRequestDto student) {
        if (studentRepository.existsById(id)){
            Student entity = toEntity(student);
            entity.setId(id);
            return Optional.of(toDto(studentRepository.save(entity)));
        }

        return Optional.empty();
    }

    public Optional<StudentResponseDto> patchStudent(Long id, StudentRequestDto studentDto) {
        return studentRepository.findById(id).map(existingStudent -> {
            updateEntity(existingStudent, studentDto);
            Student updatedStudent = studentRepository.save(existingStudent);
            return Optional.of(toDto(updatedStudent));
        }).orElse(Optional.empty());
    }



    public StudentResponseDto toDto(Student entity) {
        StudentResponseDto dto = new StudentResponseDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setPrefect(entity.isPrefect());
        dto.setEnrollmentYear(entity.getEnrollmentYear());
        dto.setGraduationYear(entity.getGraduationYear());
        dto.setSchoolYear(entity.getSchoolYear());
        dto.setGraduated(entity.isGraduated());
        dto.setHouse(entity.getHouse().getName());

        return dto;
    }

    private Student toEntity(StudentRequestDto dto) {
        Student entity = new Student();
        entity.setId(dto.id());
        entity.setFirstName(dto.firstName());
        entity.setMiddleName(dto.middleName());
        entity.setLastName(dto.lastName());
        entity.setDateOfBirth(dto.dateOfBirth());
        entity.setPrefect(dto.prefect());
        entity.setEnrollmentYear(dto.enrollmentYear());
        entity.setGraduationYear(dto.graduationYear());
        entity.setSchoolYear(dto.schoolYear());
        entity.setGraduated(dto.graduated());

        Optional<House> house = houseRepository.findById(dto.house());
        house.ifPresent(entity::setHouse);

        return entity;
    }

    public Student convertToRequestDto(StudentResponseDto responseDto) {
        Student entity = new Student();
        entity.setId(responseDto.getId());
        entity.setFirstName(responseDto.getFirstName());
        entity.setMiddleName(responseDto.getMiddleName());
        entity.setLastName(responseDto.getLastName());
        entity.setDateOfBirth(responseDto.getDateOfBirth());
        entity.setPrefect(responseDto.getPrefect());
        entity.setEnrollmentYear(responseDto.getEnrollmentYear());
        entity.setGraduationYear(responseDto.getGraduationYear());
        entity.setSchoolYear(responseDto.getSchoolYear());
        entity.setGraduated(responseDto.getGraduated());

        Optional<House> house = houseRepository.findById(responseDto.getHouse());
        house.ifPresent(entity::setHouse);

        return entity;

    }

    private void updateEntity(Student entity, StudentRequestDto dto){
        if (dto.firstName() != null) entity.setFirstName(dto.firstName());
        if (dto.middleName() != null) entity.setMiddleName(dto.middleName());
        if (dto.lastName() != null) entity.setLastName(dto.lastName());
        if (dto.dateOfBirth() != null) entity.setDateOfBirth(dto.dateOfBirth());
        if (dto.prefect() != null) entity.setPrefect(dto.prefect());
        if (dto.schoolYear() != null) entity.setSchoolYear(dto.schoolYear());
        if (dto.enrollmentYear() != null) entity.setEnrollmentYear(dto.enrollmentYear());
        if (dto.graduationYear() != null) entity.setGraduationYear(dto.graduationYear());
        if (dto.graduated() != null) entity.setGraduated(dto.graduated());
        if (dto.house() != null){
            Optional<House> house = houseRepository.findById(dto.house());
            house.ifPresent(entity::setHouse);
        }
    }
}
