package ua.com.sinenko.things.book.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.entity.Series;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    TestEntityManager entityManager;

    @Test
    void findByAuthorsName() {
        var author = entityManager.persist(Author.builder().name("Author Name").build());
        Set<Author> authors = new HashSet<>();
        authors.add(author);

        Book book = new Book();
        book.setTitle("Book Name");
        book.setGenre(Genre.builder().name("Fiction").build());
        book.setSeries(Series.builder().name("Series Name").build());
        book.setYear(new Date());
        book.setDescription("Description");
        book.setVolumeNumber("1");
        book.setAuthors(authors);


        entityManager.persist(book);
        entityManager.flush();
        entityManager.clear();


        assertNotNull(bookRepository.findByAuthorsName("Author Name"));
    }

    @Test
    void findByGenre() {
    }

    @Test
    void findByPublisher() {
    }

    @Test
    void findByYear() {
    }

    @Test
    @Transactional
    void findByTitle() {
        var author = entityManager.persist(Author.builder().name("Author Name").build());
        Set<Author> authors = new HashSet<>();
        authors.add(author);

        Book book = new Book();
        book.setTitle("Book Name");
        book.setGenre(entityManager.merge(Genre.builder().name("Fiction").build()));
        book.setSeries(entityManager.merge(Series.builder().name("Series Name").build()));
        book.setYear(new Date());
        book.setDescription("Description");
        book.setVolumeNumber("1");
        book.setAuthors(authors);


        entityManager.persist(book);
        entityManager.flush();
        entityManager.clear();

        assertNotNull(bookRepository.findByTitle("Book Name"));
    }
}