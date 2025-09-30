package ua.com.sinenko.things.pdfbook.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data@Builder
@Document(indexName = "pdfbooks")
public class CategorySchema {
    private Long id;
    private String name;
}
