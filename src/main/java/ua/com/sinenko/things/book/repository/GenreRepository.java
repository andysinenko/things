package ua.com.sinenko.things.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.book.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findGenreByName(String name);
}
