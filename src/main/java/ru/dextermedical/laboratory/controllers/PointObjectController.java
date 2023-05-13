package ru.dextermedical.laboratory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dextermedical.laboratory.models.PointObject;
import ru.dextermedical.laboratory.services.PointObjectService;

import java.util.List;

@RestController
@RequestMapping("/point-object")
@CrossOrigin()
public class PointObjectController {

    @Autowired
    private PointObjectService pointObjectService;

    @GetMapping
    public List<PointObject> getAllPointObjects() {
        return pointObjectService.getAllPointObjects();
    }

    @GetMapping("/{id}")
    public PointObject getPointObjectById(@PathVariable("id") int id) {
        return pointObjectService.getPointObjectById(id);
    }

    @PostMapping
    public PointObject createPointObject(@RequestBody PointObject pointObject) {
        return pointObjectService.createPointObject(pointObject);
    }

    @PutMapping("/{id}")
    public PointObject updatePointObject(@PathVariable("id") int id, @RequestBody PointObject pointObject) {
        return pointObjectService.updatePointObject(id, pointObject);
    }

    @DeleteMapping("/{id}")
    public void deletePointObject(@PathVariable("id") int id) {
        pointObjectService.deletePointObject(id);
    }
}
