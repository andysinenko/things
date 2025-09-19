package ua.com.sinenko.things.pdfbook.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "pdfbooks")
public class AuthorSchema {
    private String id;
    private String name;
}
