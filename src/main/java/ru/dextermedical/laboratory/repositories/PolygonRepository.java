package ru.dextermedical.laboratory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dextermedical.laboratory.models.PolygonObjects;

@Repository
public interface PolygonRepository extends JpaRepository<PolygonObjects, Long> {
}
