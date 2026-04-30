package ua.com.sinenko.things.pdfbook.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.com.sinenko.things.book.dto.AuthorResponse;
import ua.com.sinenko.things.pdfbook.dto.*;
import ua.com.sinenko.things.pdfbook.entity.PdfAuthor;
import ua.com.sinenko.things.pdfbook.repository.CategoryRepository;
import ua.com.sinenko.things.pdfbook.repository.PdfAuthorRepository;
import ua.com.sinenko.things.pdfbook.repository.PdfBookRepository;
import ua.com.sinenko.things.pdfbook.schema.PdfBookSchema;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class PdfBookService {
    private static final Logger logger = LoggerFactory.getLogger(PdfBookService.class);

    private final ElasticsearchOperations operations;
    private final PdfBookRepository pdfBookRepository;
    private final PdfAuthorRepository pdfAuthorRepository;
    private final CategoryRepository categoryRepository;

    public PdfBookSchema getBook(String id) {
        return pdfBookRepository.findById(id).orElse(null);
    }

    public Page<PdfBookSchema> getBooks(int pageNumber, int pageSize) {
        String[] fields = new String[]{"id", "title", "category", "authors", "yearOfRelease", "language", "uploadDate", "numberOfPages"};
        String[] exclude = new String[]{"content"};

        NativeQuery query = NativeQuery.builder()
                .withSourceFilter(new FetchSourceFilter(false, fields, exclude))
                .withFields(fields)
                .withPageable(PageRequest.of(pageNumber, pageSize))
                .build();

        SearchHits<PdfBookSchema> searchHits = operations.search(query, PdfBookSchema.class);

        List<PdfBookSchema> pdfBookSchemas = searchHits.stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());

        logger.info("Strored books: " + pdfBookSchemas);

        long totalHits = searchHits.getTotalHits();
        return new PageImpl<PdfBookSchema>(pdfBookSchemas, PageRequest.of(pageNumber, pageSize), totalHits);
    }

    public List<CategoryDto> getCategories() {
        return CategoryMapper.toDtoList(categoryRepository.findAll());
    }

    public List<PdfAuthorDto> getPdfAuthors() {
        return PdfAuthorMapper.toDtoList(pdfAuthorRepository.findAll());
    }

    @Transactional
    public PdfBookResponse save(MultipartFile file, Long categoryId, String yearOfRelease, String language, String inputedTitle, AuthorResponse authorResponse) throws Exception {
        String id = UUID.randomUUID().toString();

        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();

        try (InputStream stream = file.getInputStream()) {
            parser.parse(stream, handler, metadata, new ParseContext());
        }

        String title = metadata.get("dc:title");
        String yearOfReleaseParsed = metadata.get("xmp:CreateDate");
        String numberOfPages = metadata.get("xmpTPg:NPages");
        String author = metadata.get("dc:creator");

        PdfBookSchema pdfBookSchema = new PdfBookSchema();
        pdfBookSchema.setId(id);
        var category = categoryRepository.findById(categoryId).orElseGet(null);
        pdfBookSchema.setCategory(CategoryMapper.toSchema(category));

        if(title != null) {
            pdfBookSchema.setTitle(title);
        } else {
            pdfBookSchema.setTitle(inputedTitle);
        }

        if(author != null) {
            PdfAuthor authorEntity = pdfAuthorRepository.findByName(author);
            if(authorEntity != null) {
                pdfBookSchema.setAuthor(PdfAuthorMapper.toSchemaFromEntity(authorEntity));
            } else {
                PdfAuthor newAuthor = new PdfAuthor();
                newAuthor.setName(author);
                authorEntity = pdfAuthorRepository.save(newAuthor);
                pdfBookSchema.setAuthor(PdfAuthorMapper.toSchemaFromEntity(authorEntity));
            }
        } else {
            pdfBookSchema.setAuthor(PdfAuthorMapper.toSchemaFromDto(authorResponse));
        }

        pdfBookSchema.setYearOfRelease(yearOfReleaseParsed!=null?yearOfReleaseParsed:yearOfRelease);
        pdfBookSchema.setLanguage(language);
        pdfBookSchema.setNumberOfPages(numberOfPages!=null? Integer.parseInt(numberOfPages):0);

        logger.info("PdfBook before storing without content: " + pdfBookSchema.toString());
        pdfBookSchema.setContent(handler.toString());


        var book = pdfBookRepository.save(pdfBookSchema);
        var bookResponse = PdfBookMapper.entityToResponseWOContent(book);

        return bookResponse;
    }

    public void deleteBook(String id) {
        pdfBookRepository.deleteById(id);
    }
}
