package ua.com.sinenko.things.pdfbook.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.com.sinenko.things.common.exception.aop.ThLogger;
import ua.com.sinenko.things.pdfbook.dto.CategoryDto;
import ua.com.sinenko.things.pdfbook.dto.CategoryMapper;
import ua.com.sinenko.things.pdfbook.dto.PdfAuthorDto;
import ua.com.sinenko.things.pdfbook.dto.PdfAuthorMapper;
import ua.com.sinenko.things.pdfbook.entity.PdfAuthor;
import ua.com.sinenko.things.pdfbook.repository.CategoryRepository;
import ua.com.sinenko.things.pdfbook.repository.PdfAuthorRepository;
import ua.com.sinenko.things.pdfbook.schema.AuthorSchema;
import ua.com.sinenko.things.pdfbook.schema.CategorySchema;
import ua.com.sinenko.things.pdfbook.schema.PdfBook;
import ua.com.sinenko.things.pdfbook.repository.PdfBookRepository;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PdfBookService {
    private final PdfBookRepository pdfBookRepository;
    private final PdfAuthorRepository pdfAuthorRepository;
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories() {
        return CategoryMapper.toDtoList(categoryRepository.findAll());
    }

    public List<PdfAuthorDto> getPdfAuthors() {
        return PdfAuthorMapper.toDtoList(pdfAuthorRepository.findAll());
    }

    public PdfBook save(MultipartFile file, CategorySchema category, Integer yearOfRelease, String language, String inputedTitle, List<AuthorSchema> inputedAuthors) throws Exception {
        String id = UUID.randomUUID().toString();

        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();

        try (InputStream stream = file.getInputStream()) {
            parser.parse(stream, handler, metadata, new ParseContext());
        }

        String title = metadata.get("title");
        String author = metadata.get("Author");
        List<String> authorsFromMetadata = author != null ? Arrays.asList(author.split(",")) : null;


        PdfBook pdfBook = new PdfBook();
        pdfBook.setId(id);
        pdfBook.setCategory(category);

        if(!title.isEmpty() || !title.isBlank()) {
            pdfBook.setTitle(title);
        } else {
            pdfBook.setTitle(inputedTitle);
        }

        if(authorsFromMetadata != null) {
            List<PdfAuthor> authors = pdfAuthorRepository.findByNameIn(authorsFromMetadata);
            if(authors != null) {
                pdfBook.setAuthors(authors.stream().map(a -> new AuthorSchema(a.getId(), a.getName())).toList());
            } else {

                pdfBook.setAuthors(inputedAuthors);
            }
        } else {
            pdfBook.setAuthors(inputedAuthors);
        }

        pdfBook.setYearOfRelease(yearOfRelease);
        pdfBook.setLanguage(language);
        pdfBook.setContent(handler.toString());

        return pdfBookRepository.save(pdfBook);
    }

    public PdfBook getBook(String id) {
        return pdfBookRepository.findById(id).orElse(null);
    }

    @ThLogger
    public Page<PdfBook> getBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return pdfBookRepository.findAll(pageable);
    }

    public void deleteBook(String id) {
        pdfBookRepository.deleteById(id);
    }
}
