package ru.dextermedical.laboratory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dextermedical.laboratory.models.PointObject;
import ru.dextermedical.laboratory.repositories.PointObjectRepository;

import java.util.List;

@Service
public class PointObjectService {

    @Autowired
    private PointObjectRepository pointObjectRepository;

    public List<PointObject> getAllPointObjects() {
        return pointObjectRepository.findAll();
    }

    public PointObject getPointObjectById(int id) {
        return pointObjectRepository.findById(id).orElse(null);
    }

    public PointObject createPointObject(PointObject pointObject) {
        return pointObjectRepository.save(pointObject);
    }

    public PointObject updatePointObject(int id, PointObject pointObject) {
        PointObject existingPointObject = getPointObjectById(id);
        if (existingPointObject != null) {
            existingPointObject.setLongitude(pointObject.getLongitude());
            existingPointObject.setLatitude(pointObject.getLatitude());
            existingPointObject.setName(pointObject.getName());
            existingPointObject.setAddress(pointObject.getAddress());
            return pointObjectRepository.save(existingPointObject);
        }
        return null;
    }

    public void deletePointObject(int id) {
        pointObjectRepository.deleteById(id);
    }
}
