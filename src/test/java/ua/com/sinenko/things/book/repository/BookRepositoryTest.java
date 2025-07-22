package ua.com.sinenko.things.book.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.entity.Series;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("Book Repository Test")
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void beforeAll() {
        var author = entityManager.persist(Author.builder().name("Author Name").build());
        Set<Author> authors = new HashSet<>();
        authors.add(author);

        var genre = entityManager.persist(Genre.builder().name("Fiction").build());

        Book book = new Book();
        book.setTitle("Book Name");
        book.setGenre(genre);
        book.setSeries(Series.builder().name("Series Name").build());
        book.setYear(LocalDate.now());
        book.setDescription("Description");
        book.setVolumeNumber("1");
        book.setAuthors(authors);


        entityManager.persist(book);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findByAuthorsName() {
        var books = bookRepository.findByAuthorsName("Author Name");
        System.out.println("By author "+ books);
        assertTrue(books.size() > 0);
    }

    @Test
    void findByYear() {
        var books = bookRepository.findByYear(LocalDate.now());
        System.out.println("findByYear() " + books);
        assertTrue(books.size() > 0 );
    }

    @Test
    @Transactional
    void findByTitle() {
        assertTrue(bookRepository.findByTitle("Book Name").size() > 0);
    }

    @Test
    void findByGenre() {
        var genre = genreRepository.findGenreByName("Fiction");
        var books = bookRepository.findBooksByGenre(genre);
        System.out.println(genre);
        System.out.println(books);
        assertTrue(books.size() > 0);
    }
}