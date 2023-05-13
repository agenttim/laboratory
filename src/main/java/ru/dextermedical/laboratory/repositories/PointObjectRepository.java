package ru.dextermedical.laboratory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dextermedical.laboratory.models.PointObject;

public interface PointObjectRepository extends JpaRepository<PointObject, Integer> {
}
