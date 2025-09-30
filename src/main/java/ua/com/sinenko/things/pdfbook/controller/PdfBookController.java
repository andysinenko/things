package ua.com.sinenko.things.pdfbook.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.sinenko.things.book.dto.AuthorDto;
import ua.com.sinenko.things.pdfbook.dto.*;
import ua.com.sinenko.things.pdfbook.schema.Author;
import ua.com.sinenko.things.pdfbook.schema.PdfBookSchema;
import ua.com.sinenko.things.pdfbook.service.PdfBookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pdfbooks")
@RequiredArgsConstructor
public class PdfBookController {
    private static final Logger logger = LoggerFactory.getLogger(PdfBookController.class);
    private final PdfBookService pdfBookService;

    @PostMapping("/upload")
    public ResponseEntity<PdfBookResponse> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "category", required = false) Long category,
            @RequestParam(value = "yearOfRelease", required = false) String yearOfRelease,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) AuthorDto author

    ) throws Exception {
        PdfBookResponse book = pdfBookService.save(file, category, yearOfRelease, language, title, author);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PdfBookSchema> getOne(@PathVariable String id) {
        PdfBookSchema pdfBookSchema = pdfBookService.getBook(id);
        if (pdfBookSchema == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pdfBookSchema);
    }

    @GetMapping
    public ResponseEntity<PdfBookPageResponse> list() {
        var result = pdfBookService.getBooks(0, 20);
        logger.debug("content in result ", result.getContent());
        return ResponseEntity.ok(PdfBookMapper.entityToResponse(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        pdfBookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> categories() {
        return ResponseEntity.ok(pdfBookService.getCategories());
    }

    @GetMapping("/pdfautors")
    public ResponseEntity<List<PdfAuthorDto>> pdfAuthors() {
        return ResponseEntity.ok(pdfBookService.getPdfAuthors());
    }
}
