package ua.com.sinenko.things.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.book.dto.*;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.service.BookService;


@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Books controller", description = "Operations with books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "List of Books", description = "Return page of books according to pagination")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    @ResponseBody
    public ResponseEntity<BookPageResponse> getAllBooks(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int pageNumber,
            @Parameter(description = "Pages size") @RequestParam(defaultValue = "20") int pageSize
    ) {
        var booksPage = bookService.getAllBooks(pageNumber, pageSize);
        var response = BookMapper.entityToResponse(booksPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "New book", description = "Create a new book")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "BookDto",
            required = true,
            content = @Content(schema = @Schema(implementation = BookRequest.class)))
    @ApiResponse(responseCode = "201", description = "Book created successfully")
    @PostMapping
    @ResponseBody
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        var bookResponse = BookMapper.entityToResponse(bookService.saveBook(bookRequest));
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get book", description = "Return selected book by id")
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookRequest.class)))
    @GetMapping(value = "/{bookId}")
    @ResponseBody
    public ResponseEntity<BookResponse> getBookById(
            @Parameter(description = "book's id", example = "42", required = true) @PathVariable("id") Long id
    ) {
        var bookEntities = bookService.getBookById(id);
        var bookDto = BookMapper.entityToDto(bookEntities);

        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete a book", description = "Delete book by id")
    @ApiResponse(responseCode = "200", description = "Book deleted successfully")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteBookById(@Parameter(description = "book's id", example = "43", required = true) @PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Change a book", description = "Change book by id")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book dto",
            required = true,
            content = @Content(schema = @Schema(implementation = BookRequest.class)))
    @ApiResponse(responseCode = "201", description = "Book changed successfully")
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<BookResponse> updateBook(@Parameter(description = "book's id", example = "44", required = true) @PathVariable Long id, @RequestBody BookRequest bookRequest) {
        Book book = bookService.updateBook(id, bookRequest);
        var response = BookMapper.entityToResponse(book);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
