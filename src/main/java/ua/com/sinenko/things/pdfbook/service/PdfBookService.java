package ua.com.sinenko.things.pdfbook.service;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.com.sinenko.things.pdfbook.schema.AuthorSchema;
import ua.com.sinenko.things.pdfbook.schema.CategorySchema;
import ua.com.sinenko.things.pdfbook.schema.PdfBook;
import ua.com.sinenko.things.pdfbook.repository.PdfBookRepository;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class PdfBookService {

    private final PdfBookRepository pdfBookRepository;

    public PdfBookService(PdfBookRepository pdfBookRepository) {
        this.pdfBookRepository = pdfBookRepository;
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

        PdfBook book = new PdfBook();
        book.setId(id);
        book.setCategory(category);

        if(!title.isEmpty() || !title.isBlank()) {
            book.setTitle(title);
        } else {
            book.setTitle(inputedTitle);
        }
        book.setAuthors(authorsFromMetadata.stream().map(e -> new AuthorSchema(e.get)));
        book.setYearOfRelease(yearOfRelease);
        book.setLanguage(language);
        book.setContent(handler.toString());

        return pdfBookRepository.save(book);
    }

    public PdfBook getBook(String id) {
        return pdfBookRepository.findById(id).orElse(null);
    }

    public Iterable<PdfBook> getBooks() {
        return pdfBookRepository.findAll();
    }

    public void deleteBook(String id) {
        pdfBookRepository.deleteById(id);
    }
}
