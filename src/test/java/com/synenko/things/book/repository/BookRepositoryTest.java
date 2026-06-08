package com.synenko.things.book.repository;

import jakarta.transaction.Transactional;
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
import com.synenko.things.book.entity.Author;
import com.synenko.things.book.entity.Book;
import com.synenko.things.book.entity.Genre;
import com.synenko.things.book.entity.Series;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestCacheExclusionConfig.class)
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
        List<Author> authors = new ArrayList<>();
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