package ru.dextermedical.laboratory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dextermedical.laboratory.models.PolygonObjects;
import ru.dextermedical.laboratory.repositories.PolygonRepository;

import java.util.List;
import java.util.Optional;

@RequestMapping("/polygons")
@RestController
@CrossOrigin()
public class PolygonController {

    @Autowired
    private PolygonRepository polygonRepository;

    @GetMapping
    public ResponseEntity<List<PolygonObjects>> getAllPolygons() {
        List<PolygonObjects> polygons = polygonRepository.findAll();
        return ResponseEntity.ok(polygons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolygonObjects> getPolygonById(@PathVariable("id") Long id) {
        Optional<PolygonObjects> polygon = polygonRepository.findById(id);
        if (polygon.isPresent()) {
            return ResponseEntity.ok(polygon.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PolygonObjects> createPolygon(@RequestBody PolygonObjects polygon) {
        PolygonObjects saved = polygonRepository.save(polygon);
        return ResponseEntity.created(null).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolygonObjects> updatePolygon(@PathVariable("id") Long id, @RequestBody PolygonObjects polygon) {
        Optional<PolygonObjects> existing = polygonRepository.findById(id);
        if (existing.isPresent()) {
            existing.get().setName(polygon.getName());
            existing.get().setColor(polygon.getColor());
            existing.get().setPolygon(polygon.getPolygon());
            PolygonObjects updated = polygonRepository.save(existing.get());
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolygon(@PathVariable("id") Long id) {
        if (polygonRepository.existsById(id)) {
            polygonRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
