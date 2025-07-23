package ua.com.sinenko.things.place.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.com.sinenko.things.place.entity.Place;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        entityManager.persist(place);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findByName() {
        var placesStored = placeRepository.findByName(placeName);

        assertEquals(1, placesStored.size());
        assertEquals(placesStored.getFirst().getName(), placeName);
        assertEquals(placesStored.getFirst().getDescription(), placeDescription);
    }
}