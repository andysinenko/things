package com.synenko.things.book.controller;

import com.synenko.things.book.dto.BookMapper;
import com.synenko.things.book.dto.BookPageResponse;
import com.synenko.things.book.dto.BookRequest;
import com.synenko.things.book.dto.BookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synenko.things.book.dto.*;
import com.synenko.things.book.entity.Book;
import com.synenko.things.book.service.BookService;


@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Books controller", description = "Operations with books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @Operation(summary = "Get all books", description = "Returns a paginated list of books")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved page of books",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookPageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<BookPageResponse> getAllBooks(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int pageNumber,
            @Parameter(description = "Pages size") @RequestParam(defaultValue = "20") int pageSize
    ) {
        return ResponseEntity.ok(bookService.getAllBooksOld(pageNumber, pageSize));
    }


    @Operation(summary = "New book", description = "Create a new book")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "BookRequest",
            required = true,
            content = @Content(schema = @Schema(implementation = BookRequest.class)))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Book created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        var bookResponse = BookMapper.entityToResponse(bookService.saveBook(bookRequest));
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get book by id", description = "Returns a single book by its id")
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
    public ResponseEntity<BookResponse> getOne(
            @Parameter(description = "Book id", example = "42", required = true) @PathVariable Long id) {
        var bookEntities = bookService.getBookById(id);
        var bookDto = BookMapper.entityToDto(bookEntities);

        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete book by id", description = "Deletes a book by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Book deleted successfully",
                    content = @Content
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(
            @Parameter(description = "Book id", example = "43", required = true)
            @PathVariable Long id
    ) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update book by id", description = "Updates a book by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book updated successfully",
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
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Book update request",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BookRequest.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @Parameter(description = "Book id", example = "44", required = true)
            @PathVariable Long id,
            @RequestBody BookRequest bookRequest
    ) {
        Book book = bookService.updateBook(id, bookRequest);
        var response = BookMapper.entityToResponse(book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
