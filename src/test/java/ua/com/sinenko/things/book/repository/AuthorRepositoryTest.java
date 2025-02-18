package ua.com.sinenko.things.book.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.com.sinenko.things.book.entity.Author;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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