package ua.com.sinenko.things.book.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.cache.autoconfigure.CacheAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ua.com.sinenko.things.TestCacheExclusionConfig;
import ua.com.sinenko.things.book.entity.Author;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestCacheExclusionConfig.class)
@DisplayName("Author Repository Test")
class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;
    
    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("findByName test")
    void findByNameTest() {
        var authorStore = entityManager.persist(Author.builder().name("Author Name").build());
        entityManager.persist(authorStore);
        entityManager.flush();
        entityManager.clear();

        var author = authorRepository.findByName("Author Name");

        assertNotNull(author);
        assertEquals(author.getName(), "Author Name");
    }

    @Test
    @DisplayName("findAll test")
    void testFindByName() {
        var authorStore = entityManager.persist(Author.builder().name("Author Name").build());
        entityManager.persist(authorStore);
        entityManager.flush();
        entityManager.clear();

        var authors = authorRepository.findAll();

        assertTrue(authors.size() > 0 );
    }

    @Test
    @DisplayName("save test")
    void saveTest() {
        var author = entityManager.persist(Author.builder().name("Author Name").build());
        author = authorRepository.save(author);

        assertNotNull(author);
        assertEquals(author.getName(), "Author Name");
    }
}