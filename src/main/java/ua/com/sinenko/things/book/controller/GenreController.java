package ua.com.sinenko.things.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.sinenko.things.book.dto.GenreRequest;
import ua.com.sinenko.things.book.dto.GenreResponse;
import ua.com.sinenko.things.book.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@AllArgsConstructor
@Tag(name = "Genres controller", description = "Operations with genres of the books")
public class GenreController {
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
}
