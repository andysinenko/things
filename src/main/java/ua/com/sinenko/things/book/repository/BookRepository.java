package ua.com.sinenko.things.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorsName(String name);

    List<Book> findByGenre(Genre genre);

    List<Book> findByPublisher(String publisher);

    List<Book> findByYear(Date year);

    List<Book> findByTitle(String title);

}
