package ua.com.sinenko.things.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.book.entity.Author;

import java.util.Collection;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);

    List<Author> findByIdIn(Collection<Long> authorIds);
}
