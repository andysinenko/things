package ua.com.sinenko.things.pdfbook.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "pdfbooks")
@Data
@NoArgsConstructor
public class PdfBookSchema {
    @Id
    private String id;
    private String title;
    private Author author;
    private CategorySchema category;
    private String yearOfRelease;
    private String language;
    private String content;
    private String filePath;
    Integer numberOfPages;
    private LocalDateTime uploadDate;
}
