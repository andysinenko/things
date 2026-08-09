package com.synenko.things.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synenko.things.book.dto.AuthorResponse;
import com.synenko.things.book.dto.AuthorMapper;
import com.synenko.things.book.dto.AuthorRequest;
import com.synenko.things.book.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@AllArgsConstructor
@Tag(name = "Authors controller", description = "Operations with authors of the books")
public class AuthorController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private AuthorService authorService;

    @Operation(summary = "Get list of authors", description = "Return all book's authors")
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponse.class)))
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllBooks() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    @Operation(summary = "New author", description = "Create a new author")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "AuthorRequest",
            required = true,
            content = @Content(schema = @Schema(implementation = AuthorRequest.class)))
    @ApiResponse(responseCode = "201", description = "Created")
    @PostMapping
    public ResponseEntity<AuthorResponse> addAuthor(@RequestBody AuthorRequest authorRequest) {
        var author = authorService.saveAuthor(authorRequest);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthor(@RequestBody AuthorRequest authorRequest, @PathVariable Long id) {
        logger.info("Updating author with id {}", id);
        logger.info("author {}", authorRequest);

        authorService.updateAuthor(authorRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        logger.info("Delete author with id {}", id);
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
