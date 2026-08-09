package com.synenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name="GenreResponse", description = "Dto object for book's genre")
public record GenreResponse(
        @Schema(description = "Genre ID")
        Long id,
        @Schema(description = "genre name")
        String name,
        @Schema(description = "genre note")
        String note
) {
}

