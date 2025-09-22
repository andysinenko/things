package ua.com.sinenko.things.pdfbook.schema;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.util.List;

@Document(indexName = "pdfbooks")
@Data
@NoArgsConstructor
public class PdfBook {
    @Id
    private String id;
    private String title;
    private List<AuthorSchema> authors;
    private CategorySchema category;
    private Integer yearOfRelease;
    private String language;
    private String content;
    private String filePath;
    private LocalDate uploadDate;
}
