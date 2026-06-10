package com.synenko.things.pdfbook.service;

import com.synenko.things.pdfbook.dto.*;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.synenko.things.pdfbook.dto.*;
import com.synenko.things.pdfbook.entity.PdfAuthor;
import com.synenko.things.pdfbook.entity.PdfBook;
import com.synenko.things.pdfbook.repository.CategoryRepository;
import com.synenko.things.pdfbook.repository.PdfAuthorRepository;
import com.synenko.things.pdfbook.repository.PdfBookRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional(readOnly = true)
    public PdfBookPageResponse getBooks(int pageNumber, int pageSize) {
        Page<PdfBook> page = pdfBookRepository.findAllWithAssociations(
                PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "uploadDate"))
        );
        return PdfBookMapper.entityToResponse(page);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories() {
        return CategoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<PdfAuthorResponse> getPdfAuthors() {
        return PdfAuthorMapper.toDtoList(pdfAuthorRepository.findAll());
    }

    @Transactional
    public PdfBookResponse save(MultipartFile file, Long categoryId, String yearOfRelease,
                                String language, String inputedTitle,
                                Long authorId) throws Exception {  // ← было AuthorResponse authorResponse

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
            // приоритет — метаданные из PDF
            author = pdfAuthorRepository.findByName(authorName);
            if (author == null) {
                author = pdfAuthorRepository.save(PdfAuthor.builder().name(authorName).build());
            }
        } else {
            // автор выбранный пользователем в форме
            author = authorId != null
                    ? pdfAuthorRepository.findById(authorId).orElse(null)  // ← было PdfAuthorMapper.fromAuthorResponse()
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
        logger.debug("Saving PdfBook: {}", saved);

        return PdfBookMapper.toResponse(pdfBookRepository.save(saved));
    }

    @Transactional
    public void deleteBook(Long id) {
        pdfBookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PdfBook> findByTitle(String title) {
        return pdfBookRepository.findByTitleContainingIgnoreCase(title);
    }
}