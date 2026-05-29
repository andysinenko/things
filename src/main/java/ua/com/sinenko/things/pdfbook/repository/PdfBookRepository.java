package ua.com.sinenko.things.pdfbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.sinenko.things.pdfbook.entity.PdfBook;

import java.util.List;

@Repository
public interface PdfBookRepository extends JpaRepository<PdfBook, Long> {
    List<PdfBook> findByTitleContainingIgnoreCase(String title);

    @Query(
            value = "SELECT b FROM PdfBook b LEFT JOIN FETCH b.author LEFT JOIN FETCH b.category",
            countQuery = "SELECT COUNT(b) FROM PdfBook b"
    )
    Page<PdfBook> findAllWithAssociations(Pageable pageable);

    public interface PdfBookSummary {
        Long getId();
        String getTitle();
        String getYearOfRelease();
        String getLanguage();
        String getAuthorName();
        String getCategoryName();
    }

    @Query("SELECT b.id AS id, b.title AS title, b.yearOfRelease AS yearOfRelease, " +
            "b.language AS language, b.author.name AS authorName, b.category.name AS categoryName " +
            "FROM PdfBook b")
    Page<PdfBookSummary> findAllSummaries(Pageable pageable);
}


