package ua.com.sinenko.things.pdfbook.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.sinenko.things.book.dto.AuthorResponse;
import ua.com.sinenko.things.pdfbook.dto.*;
import ua.com.sinenko.things.pdfbook.entity.PdfBook;
import ua.com.sinenko.things.pdfbook.service.PdfBookService;

import java.nio.file.Path;
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
            @RequestParam(value = "author", required = false) AuthorResponse author
    ) throws Exception {
        return ResponseEntity.ok(pdfBookService.save(file, category, yearOfRelease, language, title, author));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PdfBook> getOne(@PathVariable Long id) {
        PdfBook book = pdfBookService.getBook(id);
        if (book == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        PdfBook book = pdfBookService.getBook(id);
        if (book == null || book.getFilePath() == null) return ResponseEntity.notFound().build();

        Path path = Path.of(book.getFilePath());
        Resource resource = new FileSystemResource(path);
        if (!resource.exists()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
    }

    @GetMapping
    public ResponseEntity<PdfBookPageResponse> list() {
        return ResponseEntity.ok(PdfBookMapper.entityToResponse(pdfBookService.getBooks(0, 20)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
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

    @GetMapping("/search")
    public ResponseEntity<List<PdfBookResponse>> search(@RequestParam String title) {
        return ResponseEntity.ok(
                pdfBookService.findByTitle(title).stream()
                        .map(PdfBookMapper::toResponse)
                        .toList()
        );
    }
}