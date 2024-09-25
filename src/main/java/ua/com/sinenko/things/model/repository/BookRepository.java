package ua.com.sinenko.things.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.sinenko.things.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
