package ua.com.sinenko.things.book.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {
    List<Book> findByAuthorsName(String name);

    List<Book> findBooksByGenre(Genre genre);

    List<Book> findByYear(LocalDate year);

    List<Book> findByTitle(String title);

    @Query(value = """
    SELECT DISTINCT b FROM Book b
    LEFT JOIN FETCH b.genre
    LEFT JOIN FETCH b.series
    LEFT JOIN FETCH b.place
    """)
    Page<Book> findAll(Pageable pageable);

    @Query(value = "SELECT b FROM Book b",
            countQuery = "SELECT COUNT(b) FROM Book b")
    Page<Book> findAllPaged(Pageable pageable);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.authors WHERE b.id IN :ids")
    List<Book> findAllWithAuthorsByIds(@Param("ids") List<Long> ids);

    // Single query fetching everything needed
    @Query(value = """
    SELECT DISTINCT b FROM Book b
    LEFT JOIN FETCH b.authors
    LEFT JOIN FETCH b.genre
    LEFT JOIN FETCH b.series
    LEFT JOIN FETCH b.place
    WHERE b.id IN :ids
    """)
    List<Book> findAllWithAssociationsByIds(@Param("ids") List<Long> ids);

    // Lightweight paged query — only scalar data, no associations
    @Query(value = "SELECT b.id FROM Book b",
            countQuery = "SELECT COUNT(b.id) FROM Book b")
    Page<Long> findAllIds(Pageable pageable);
}
