package ua.com.sinenko.things.pdfbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.pdfbook.entity.PdfAuthor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PdfAuthorRepository extends JpaRepository<PdfAuthor, Long> {
    Optional<PdfAuthor> findAllByNameIn(Collection<String> names);
    List<PdfAuthor> findByNameIn(Collection<String> names);
    PdfAuthor findByName(String name);
}
