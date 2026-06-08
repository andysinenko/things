package com.synenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import com.synenko.things.place.dto.PlaceResponse;

import java.util.List;

@Builder
@Schema(name = "BookResponse", description = "Book's response object")
public record BookResponse(
        @Schema(name = "id", description = "Book's id", example = "1")
        Long id,
        @Schema(name = "title", description = "Book's title", example = "Kobzar")
        String title,
        @Schema(name = "authors", description = "List of authors of the book")
        List<AuthorResponse> authors,
        @Schema(name = "genre", description = "Genre of the book")
        GenreResponse genre,
        @Schema(name = "series", description = "Series of the book")
        SeriesResponse series,
        @Schema(name = "year", description = "Date of issue")
        String year,
        @Schema(name = "place", description = "Place of storing")
        PlaceResponse place,
        @Schema(name = "volume", description = "Volume")
        String volume,
        @Schema(name = "description", description = "Additional information")
        String description) {}
