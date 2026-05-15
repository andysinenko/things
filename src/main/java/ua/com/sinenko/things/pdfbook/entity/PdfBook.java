package ua.com.sinenko.things.pdfbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pdf_books", schema = "things")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PdfBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private PdfAuthor author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String yearOfRelease;
    private String language;
    private String filePath;
    private Integer numberOfPages;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
}
