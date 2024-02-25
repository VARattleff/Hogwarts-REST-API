package edu.hogwarts.studentadmin.controllers;

import edu.hogwarts.studentadmin.dto.HouseResponseDto;
import edu.hogwarts.studentadmin.services.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HouseController {
    private final HouseService houseService;


    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public ResponseEntity<List<HouseResponseDto>> getAll() {
        var houses = houseService.findAll();
        if (houses.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(houses);
    }

    @GetMapping("/{name}")
    public ResponseEntity<HouseResponseDto> get(@PathVariable String  name) {
        var house = this.houseService.findById(name);
        return house.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
