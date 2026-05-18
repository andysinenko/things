package ua.com.sinenko.things.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.book.dto.AuthorResponse;
import ua.com.sinenko.things.book.dto.AuthorMapper;
import ua.com.sinenko.things.book.dto.AuthorRequest;
import ua.com.sinenko.things.book.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@AllArgsConstructor
@Tag(name = "Authors controller", description = "Operations with authors of the books")
public class AuthorController {
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
    public ResponseEntity<Void> addAuthor(@RequestBody AuthorRequest authorRequest) {
        authorService.saveAuthor(AuthorMapper.requestToEntity(authorRequest));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
