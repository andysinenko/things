package ua.com.sinenko.things.pdfbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.sinenko.things.pdfbook.entity.PdfAuthor;

import java.util.Collection;
import java.util.Optional;

public interface PdfAuthorRepository extends JpaRepository<PdfAuthor, Long> {
    Optional<PdfAuthor> findAllByNameIn(Collection<String> names);
}
