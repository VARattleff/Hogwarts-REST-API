package edu.hogwarts.studentadmin.services;

import edu.hogwarts.studentadmin.dto.TeacherRequestDto;
import edu.hogwarts.studentadmin.dto.TeacherResponseDto;
import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Teacher;
import edu.hogwarts.studentadmin.repository.HouseRepository;
import edu.hogwarts.studentadmin.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final HouseRepository houseRepository;

    public TeacherService(TeacherRepository teacherRepository, HouseRepository houseRepository) {
        this.teacherRepository = teacherRepository;
        this.houseRepository = houseRepository;
    }

    public List<TeacherResponseDto> findAll(){
        return teacherRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<TeacherResponseDto> findById(Long id){
        return teacherRepository.findById(id).map(this::toDto);
    }

    public TeacherResponseDto save(TeacherRequestDto teacher){
        return toDto(teacherRepository.save(toEntity(teacher)));
    }

    public Optional<TeacherResponseDto> deleteById(Long id){
        Optional<TeacherResponseDto> existingTeacher = findById(id);
        teacherRepository.deleteById(id);
        return existingTeacher;
    }

    public Optional<TeacherResponseDto> updateIfExist(Long id, TeacherRequestDto teacher){
        if (teacherRepository.existsById(id)){
            Teacher entity = toEntity(teacher);
            entity.setId(id);
            return Optional.of(toDto(teacherRepository.save(entity)));
        }
        return Optional.empty();
    }

    public Optional<TeacherResponseDto> patchStudent(Long id, TeacherRequestDto studentDto) {
        return teacherRepository.findById(id).map(existingStudent -> {
            updateEntity(existingStudent, studentDto);
            Teacher updatedStudent = teacherRepository.save(existingStudent);
            return Optional.of(toDto(updatedStudent));
        }).orElse(Optional.empty());
    }

    public TeacherResponseDto toDto(Teacher entity){
        TeacherResponseDto dto = new TeacherResponseDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setHeadOfHouse(entity.getHeadOfHouse());
        dto.setEmployment(entity.getEmployment());
        dto.setEmploymentStart(entity.getEmploymentStart());
        dto.setEmploymentEnd(entity.getEmploymentEnd());
        if(entity.getHouse() != null) {
            dto.setHouse(entity.getHouse().getName());
        }

        return dto;
    }

    public Teacher toEntity(TeacherRequestDto dto){
        Teacher entity = new Teacher();
        entity.setId(dto.id());
        entity.setFirstName(dto.firstName());
        entity.setMiddleName(dto.middleName());
        entity.setLastName(dto.lastName());
        entity.setDateOfBirth(dto.dateOfBirth());
        entity.setHeadOfHouse(dto.headOfHouse());
        entity.setEmployment(dto.employment());
        entity.setEmploymentStart(dto.employmentStart());
        entity.setEmploymentEnd(dto.employmentEnd());

        Optional<House> house = houseRepository.findById(dto.house());
        house.ifPresent(entity::setHouse);

        return entity;
    }

    private void updateEntity(Teacher entity, TeacherRequestDto dto){
        if (dto.firstName() != null) entity.setFirstName(dto.firstName());
        if (dto.lastName() != null) entity.setLastName(dto.lastName());
        if (dto.middleName() != null) entity.setMiddleName(dto.middleName());
        if (dto.dateOfBirth() != null) entity.setDateOfBirth(dto.dateOfBirth());
        if (dto.headOfHouse() != null) entity.setHeadOfHouse(dto.headOfHouse());
        if (dto.employment() != null) entity.setEmployment(dto.employment());
        if (dto.employmentStart() != null) entity.setEmploymentStart(dto.employmentStart());
        if (dto.employmentEnd() != null) entity.setEmploymentEnd(dto.employmentEnd());
        if (dto.house() != null){
            Optional<House> house = houseRepository.findById(dto.house());
            house.ifPresent(entity::setHouse);
        }

    }
}
