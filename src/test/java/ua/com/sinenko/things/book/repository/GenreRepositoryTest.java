package ua.com.sinenko.things.book.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.com.sinenko.things.book.entity.Genre;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Genre Repository Test")
class GenreRepositoryTest {
    private static final String genreName1 = "Historical fiction";
    private static final String genreName2 = "Scince fiction";

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Find Genre By Name")
    void findGenreByNameTest() {
        var genre = Genre.builder().name(genreName1).build();

        entityManager.persist(genre);
        entityManager.flush();
        entityManager.clear();

        var storedGenre = genreRepository.findGenreByName(genreName1);

        assertEquals(storedGenre.getName(), genreName1);
    }

    @Test
    @DisplayName("Find All")
    void findAllGenresTest() {
        var genre1 = Genre.builder().name(genreName1).build();
        var genre2 = Genre.builder().name(genreName2).build();

        entityManager.persist(genre1);
        entityManager.persist(genre2);
        entityManager.flush();
        entityManager.clear();

        var storedGenres = genreRepository.findAll();

        assertEquals(storedGenres.size(), 2);

        assertEquals(storedGenres.get(0).getName(), genreName1);
        assertEquals(storedGenres.get(1).getName(), genreName2);
    }
}