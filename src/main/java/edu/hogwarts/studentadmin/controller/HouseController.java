package edu.hogwarts.studentadmin.controller;

import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.repository.HouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HouseController {
    private final HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public ResponseEntity<List<House>> getAll(){
        var houses = this.houseRepository.findAll();
        if(!houses.isEmpty()) {
            return ResponseEntity.ok(houses);
        }
        return ResponseEntity.noContent().build();
    }
}
