package ua.com.sinenko.things.pdfbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.sinenko.things.pdfbook.dto.CategoryDto;
import ua.com.sinenko.things.pdfbook.dto.PdfAuthorDto;
import ua.com.sinenko.things.pdfbook.dto.PdfBookMapper;
import ua.com.sinenko.things.pdfbook.dto.PdfBookPageResponse;
import ua.com.sinenko.things.pdfbook.schema.AuthorSchema;
import ua.com.sinenko.things.pdfbook.schema.CategorySchema;
import ua.com.sinenko.things.pdfbook.schema.PdfBook;
import ua.com.sinenko.things.pdfbook.service.PdfBookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pdfbooks")
@RequiredArgsConstructor
public class PdfBookController {
    private final PdfBookService pdfBookService;

    @PostMapping("/upload")
    public ResponseEntity<PdfBook> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "category", required = false) CategorySchema category,
            @RequestParam(value = "yearOfRelease", required = false) Integer yearOfRelease,
            @RequestParam(value = "language", required = false) String language,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "authors", required = false) List<AuthorSchema> authors

    ) throws Exception {
        PdfBook book = pdfBookService.save(file, category, yearOfRelease, language, title, authors);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PdfBook> getOne(@PathVariable String id) {
        PdfBook pdfBook = pdfBookService.getBook(id);
        if (pdfBook == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(pdfBook);
    }

    @GetMapping
    public ResponseEntity<PdfBookPageResponse> list() {
        return ResponseEntity.ok(PdfBookMapper.entityToResponse(pdfBookService.getBooks(0, 20)));
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
