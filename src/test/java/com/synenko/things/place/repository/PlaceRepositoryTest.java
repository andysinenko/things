package com.synenko.things.place.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import com.synenko.things.TestCacheExclusionConfig;
import com.synenko.things.place.entity.Place;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestCacheExclusionConfig.class)
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