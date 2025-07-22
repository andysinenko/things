package ua.com.sinenko.things.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.book.entity.Series;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
}
