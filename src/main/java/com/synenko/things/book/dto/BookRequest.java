package com.synenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;


@Builder
@Schema(name = "Book request", description = "Book's dto object")
public record BookRequest(
    @Schema(name = "title", description = "Book's title", example = "Kobzar")
    String title,
    @Schema(name = "authors", description = "List of authors of the book")
    List<Long> authors,
    @Schema(name = "genre", description = "Genre of the book")
    Long genre,
    @Schema(name = "series", description = "Series of the book")
    Long series,
    @Schema(name = "place", description = "Place of storing")
    Long place,
    @Schema(name = "year", description = "Date of issue")
    LocalDate year,
    @Schema(name = "volume", description = "Volume")
    String volume,
    @Schema(name = "description", description = "Additional information")
    String description) {}
