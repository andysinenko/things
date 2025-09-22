package ua.com.sinenko.things.pdfbook.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ua.com.sinenko.things.pdfbook.schema.PdfBook;
//todo add: public interface PdfBookSummary without content for findAll
public interface PdfBookRepository extends ElasticsearchRepository<PdfBook, String> {
}
