package ua.com.sinenko.things.pdfbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ua.com.sinenko.things.book.dto.BookPageResponse;
import ua.com.sinenko.things.book.dto.BookResponse;
import ua.com.sinenko.things.pdfbook.dto.*;
import ua.com.sinenko.things.pdfbook.entity.PdfBook;
import ua.com.sinenko.things.pdfbook.service.PdfBookService;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pdfbooks")
@RequiredArgsConstructor
@Tag(name = "PdfBooks controller", description = "Operations with pdf books")
public class PdfBookController {
    private static final Logger logger = LoggerFactory.getLogger(PdfBookController.class);
    private final PdfBookService pdfBookService;

    @Operation(
            summary = "Upload pdf book",
            description = "Upload and store a new PDF book with optional metadata"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "PDF book successfully uploaded and stored",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PdfBookResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters or unsupported file format",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PdfBookResponse> upload(
            @Parameter(description = "PDF file to upload", required = true,
                    schema = @Schema(type = "string", format = "binary"))
            @RequestParam("file") MultipartFile file,

            @Parameter(description = "Category ID of the book")
            @RequestParam(value = "category", required = false) Long category,

            @Parameter(description = "Year the book was released, e.g. 2023")
            @RequestParam(value = "yearOfRelease", required = false) String yearOfRelease,

            @Parameter(description = "Language of the book, e.g. English")
            @RequestParam(value = "language", required = false) String language,

            @Parameter(description = "Title of the book")
            @RequestParam(value = "title", required = false) String title,

            @Parameter(description = "Author associated with the book")
            @RequestParam(value = "author", required = false) AuthorResponse author
    ) throws Exception {
        return ResponseEntity.ok(pdfBookService.save(file, category, yearOfRelease, language, title, author));
    }

    @Operation(summary = "Get pdf book by id", description = "Returns a single pdf book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved book",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PdfBook> getOne(@Parameter(description = "pdf book id", example = "42", required = true) @PathVariable Long id) {
        PdfBook book = pdfBookService.getBook(id);
        if (book == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book);
    }

    @Operation(
            summary = "Download pdf book",
            description = "Download a PDF file by book ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "PDF file successfully returned",
                    content = @Content(
                            mediaType = "application/pdf",
                            schema = @Schema(type = "string", format = "binary")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found or file does not exist",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
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

    @Operation(summary = "List pdf books", description = "Get paginated list of pdf books")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of pdf books",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PdfBookPageResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<PdfBookPageResponse> getAllPdfbooks(@Parameter(description = "Page number") @RequestParam(defaultValue = "0") int pageNumber,
                                                    @Parameter(description = "Pages size") @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(pdfBookService.getBooks(pageNumber, pageSize));
    }

    @Operation(summary = "Delete pdf book", description = "Delete a pdf book by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Book successfully deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to delete", required = true)
            @PathVariable Long id
    ) {
        pdfBookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List categories", description = "Get all available book categories")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of categories",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> categories() {
        return ResponseEntity.ok(pdfBookService.getCategories());
    }


    @Operation(summary = "List pdf authors", description = "Get all available pdf book authors")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of pdf authors",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PdfAuthorResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/pdfautors")
    public ResponseEntity<List<PdfAuthorResponse>> pdfAuthors() {
        return ResponseEntity.ok(pdfBookService.getPdfAuthors());
    }

    @Operation(summary = "Search pdf books by title", description = "Find pdf books matching the given title")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved matching books",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PdfBookResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<List<PdfBookResponse>> search(
            @Parameter(description = "Title to search for", required = true)
            @RequestParam String title
    ) {
        return ResponseEntity.ok(
                pdfBookService.findByTitle(title).stream()
                        .map(PdfBookMapper::toResponse)
                        .toList()
        );
    }
}