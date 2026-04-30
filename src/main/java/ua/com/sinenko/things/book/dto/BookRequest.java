package ua.com.sinenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ua.com.sinenko.things.place.dto.PlaceRequest;

import java.time.LocalDate;
import java.util.List;


@Builder
@Schema(name = "Book request", description = "Book's dto object")
public record BookRequest(
    @Schema(name = "title", description = "Book's title", example = "Kobzar")
    String title,
    @Schema(name = "authors", description = "List of authors of the book")
    List<AuthorRequest> authors,
    @Schema(name = "genre", description = "Genre of the book")
    GenreRequest genre,
    @Schema(name = "series", description = "Series of the book")
    SeriesRequest series,
    @Schema(name = "year", description = "Date of issue")
    LocalDate year,
    @Schema(name = "place", description = "Place of storing")
    PlaceRequest place,
    @Schema(name = "volume", description = "Volume")
    String volume,
    @Schema(name = "description", description = "Additional information")
    String description) {}
