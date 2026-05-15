package ua.com.sinenko.things.pdfbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pdfbooks_authors", schema="things")
@AllArgsConstructor
@NoArgsConstructor
@Data@Builder
public class PdfAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pdf_authors_seq")
    @SequenceGenerator(name = "pdf_authors_seq", sequenceName = "things.pdf_authors_sequence", allocationSize = 1)
    private Long id;

    private String name;
}
