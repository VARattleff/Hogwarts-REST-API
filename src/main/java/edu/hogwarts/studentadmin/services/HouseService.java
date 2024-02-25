package edu.hogwarts.studentadmin.services;

import edu.hogwarts.studentadmin.dto.HouseResponseDto;
import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.repository.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseService {
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<HouseResponseDto> findAll(){
        return houseRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<HouseResponseDto> findById(String name){
        return houseRepository.findById(name).map(this::toDto);
    }

    public HouseResponseDto toDto(House entity){
        HouseResponseDto dto = new HouseResponseDto();
        dto.setName(entity.getName());
        dto.setFounder(entity.getFounder());
        dto.setColors(entity.getColors());

        return dto;
    }
}
