package ua.com.sinenko.things.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.sinenko.things.model.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
