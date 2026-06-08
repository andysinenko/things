package com.synenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name="GenreResponse", description = "Dto object for book's genre")
public record GenreResponse(
        @Schema(name="id", description = "Genre ID")
        Long id,
        @Schema(name="name", description = "Schema name")
        String name) {
}

