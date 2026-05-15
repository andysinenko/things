package ua.com.sinenko.things.pdfbook.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.com.sinenko.things.book.dto.AuthorResponse;
import ua.com.sinenko.things.pdfbook.dto.*;
import ua.com.sinenko.things.pdfbook.entity.PdfAuthor;
import ua.com.sinenko.things.pdfbook.entity.PdfBook;
import ua.com.sinenko.things.pdfbook.repository.CategoryRepository;
import ua.com.sinenko.things.pdfbook.repository.PdfAuthorRepository;
import ua.com.sinenko.things.pdfbook.repository.PdfBookRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PdfBookService {
    private static final Logger logger = LoggerFactory.getLogger(PdfBookService.class);

    @Value("${things.pdfbooks.storage-path}")
    private String storagePath;

    private final PdfBookRepository pdfBookRepository;
    private final PdfAuthorRepository pdfAuthorRepository;
    private final CategoryRepository categoryRepository;

    public PdfBook getBook(Long id) {
        return pdfBookRepository.findById(id).orElse(null);
    }

    public Page<PdfBook> getBooks(int pageNumber, int pageSize) {
        return pdfBookRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public List<CategoryDto> getCategories() {
        return CategoryMapper.toDtoList(categoryRepository.findAll());
    }

    public List<PdfAuthorDto> getPdfAuthors() {
        return PdfAuthorMapper.toDtoList(pdfAuthorRepository.findAll());
    }

    @Transactional
    public PdfBookResponse save(MultipartFile file, Long categoryId, String yearOfRelease,
                                String language, String inputedTitle,
                                AuthorResponse authorResponse) throws Exception {

        byte[] bytes = file.getBytes();

        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();

        try (InputStream stream = new ByteArrayInputStream(bytes)) {
            parser.parse(stream, handler, metadata, new ParseContext());
        }

        String title         = metadata.get("dc:title");
        String yearParsed    = metadata.get("xmp:CreateDate");
        String numberOfPages = metadata.get("xmpTPg:NPages");
        String authorName    = metadata.get("dc:creator");

        var category = categoryId != null
                ? categoryRepository.findById(categoryId).orElse(null)
                : null;

        PdfAuthor author;
        if (authorName != null) {
            author = pdfAuthorRepository.findByName(authorName);
            if (author == null) {
                author = pdfAuthorRepository.save(PdfAuthor.builder().name(authorName).build());
            }
        } else {
            author = authorResponse != null
                    ? PdfAuthorMapper.fromAuthorResponse(authorResponse)
                    : null;
        }

        PdfBook book = PdfBook.builder()
                .title(title != null ? title : inputedTitle)
                .author(author)
                .category(category)
                .yearOfRelease(yearParsed != null ? yearParsed : yearOfRelease)
                .language(language)
                .numberOfPages(numberOfPages != null ? Integer.parseInt(numberOfPages) : 0)
                .uploadDate(LocalDateTime.now())
                .build();

        PdfBook saved = pdfBookRepository.save(book);

        String fileName = saved.getId() + "_" + file.getOriginalFilename();
        Path destination = Path.of(storagePath, fileName);
        Files.createDirectories(destination.getParent());
        Files.write(destination, bytes);

        saved.setFilePath(destination.toString());

        logger.info("Saving PdfBook: {}", saved);

        return PdfBookMapper.toResponse(pdfBookRepository.save(saved));
    }

    public void deleteBook(Long id) {
        pdfBookRepository.deleteById(id);
    }

    public List<PdfBook> findByTitle(String title) {
        return pdfBookRepository.findByTitleContainingIgnoreCase(title);
    }
}