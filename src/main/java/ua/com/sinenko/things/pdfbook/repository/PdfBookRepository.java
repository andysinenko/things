package ua.com.sinenko.things.pdfbook.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.com.sinenko.things.pdfbook.schema.PdfBookSchema;

public interface PdfBookRepository extends ElasticsearchRepository<PdfBookSchema, String> {

}
