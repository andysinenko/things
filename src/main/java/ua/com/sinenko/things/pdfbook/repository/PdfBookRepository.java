package ua.com.sinenko.things.pdfbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.pdfbook.entity.PdfBook;

import java.util.List;

@Repository
public interface PdfBookRepository extends JpaRepository<PdfBook, Long> {
    List<PdfBook> findByTitleContainingIgnoreCase(String title);
}


