package ua.com.sinenko.things.place.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.com.sinenko.things.place.entity.Place;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Place Repository Test")
class PlaceRepositoryTest {
    private final String placeName = "dacha";
    private final String placeDescription = "our country house";

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        Place place = Place.builder().name(placeName).description(placeDescription).build();

        var placeStored = entityManager.persist(place);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findByName() {
        var placesStored = placeRepository.findByName(placeName);

        assertEquals(placesStored.size(), 1);
        assertEquals(placesStored.get(0).getName(), placeName);
        assertEquals(placesStored.get(0).getDescription(), placeDescription);
    }

    @Test
    void findByDescription() {
        var placesStored = placeRepository.findByDescription(placeDescription);

        assertEquals(placesStored.size(), 1);
        assertEquals(placesStored.get(0).getName(), placeName);
        assertEquals(placesStored.get(0).getDescription(), placeDescription);
    }
}