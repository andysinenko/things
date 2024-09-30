package ua.com.sinenko.things.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.book.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorsName(String name);

    List<Book> findByGenre(String genre);

    List<Book> findByPublisher(String publisher);

    List<Book> findByYear(String year);

    List<Book> findByPlaceId(String placeId);

    List<Book> findByNamer(String name);
}
