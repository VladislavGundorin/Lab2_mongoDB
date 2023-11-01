package org.example.controller;

import org.example.entity.Auto;
import org.example.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/autos")
public class AutoController {
    private final AutoService autoService;

    @Autowired
    public AutoController(AutoService autoService) {
        this.autoService = autoService;
    }

    @PostMapping("/create")
    public Auto createAuto(@RequestBody Auto auto) {
        return autoService.createAuto(auto);
    }

    @GetMapping("/{id}")
    public Auto getAutoById(@PathVariable String id) {
        return autoService.getAutoById(id);
    }

    @PutMapping("/edit/{id}")
    public Auto updateAuto(@PathVariable String id, @RequestBody Auto auto) {
        return autoService.updateAuto(id, auto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuto(@PathVariable String id) {
        autoService.deleteAuto(id);
    }

    @GetMapping("/all")
    public List<Auto> getAllAutos() {
        return autoService.getAllAutos();
    }
    @DeleteMapping("/{autoId}")
    public ResponseEntity<String> deleteAutoIfAnyFieldIsEmpty(@PathVariable String autoId) {
        autoService.deleteIfAnyFieldIsEmpty(autoId);
        return ResponseEntity.ok("Автомобиль успешно удален, если хотя бы одно поле было пустым.");
    }
    @GetMapping("/countByType/{type}")
    public ResponseEntity<Long> countAutosByType(@PathVariable String type) {
        long count = autoService.countAutosByType(type);
        return ResponseEntity.ok(count);
    }
}
