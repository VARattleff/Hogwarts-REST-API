package edu.hogwarts.studentadmin.controller;

import edu.hogwarts.studentadmin.models.House;
import edu.hogwarts.studentadmin.models.Student;
import edu.hogwarts.studentadmin.repository.HouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/House")
public class HouseController {
    private final HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public ResponseEntity<List<House>> getAll() {
        var Houses = this.houseRepository.findAll();
        if (!Houses.isEmpty()) {
            return ResponseEntity.ok(Houses);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> get(@PathVariable Long id) {
        var Houses = this.houseRepository.findById(id);
        return Houses.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<House> delete(@PathVariable("id") Long id) {
        var HouseToDelete = this.houseRepository.findById(id);
        if (HouseToDelete.isPresent()) {
            this.houseRepository.delete(houseRepository.get());
            return ResponseEntity.ok(houseRepository.get());
        }
        return ResponseEntity.notFound().build();
    }
}
