package ua.com.sinenko.things.pdfbook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Table(name = "pdfbooks_authors", schema="things")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PdfAuthor {
    @Id
    private Long id;

    private String name;
}
