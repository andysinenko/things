package com.synenko.things.book.controller;

import com.synenko.things.book.dto.AuthorResponse;
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
import com.synenko.things.book.dto.GenreRequest;
import com.synenko.things.book.dto.GenreResponse;
import com.synenko.things.book.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@AllArgsConstructor
@Tag(name = "Genres controller", description = "Operations with genres of the books")
public class GenreController {
    private static final Logger logger = LoggerFactory.getLogger(GenreController.class);

    private GenreService genreService;

    @Operation(
            summary = "Get all genres",
            description = "Return all book's genres"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenreRequest.class)
            )
    )
    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get all Authors by Genre ID",
            description = "Return all authors related to genre"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenreRequest.class)
            )
    )
    @GetMapping("/{id}/authors")
    public ResponseEntity<List<AuthorResponse>> getAllAuthorsByGenre(@PathVariable Long id) {
        return new ResponseEntity<>(genreService.getAllAuthorsByGenre(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGenre(@RequestBody GenreRequest genreRequest, @PathVariable Long id) {
        genreService.updateGenre(genreRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<GenreResponse> createGenre(@RequestBody GenreRequest genreRequest) {
        var genre = genreService.saveGenre(genreRequest);
        return new ResponseEntity<>(genre, HttpStatus.CREATED);
    }
}
