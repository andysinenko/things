package com.synenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "AuthorDto", description = "Author of the book")
public record AuthorResponse(
        @Schema(description = "Author id", example = "1") Long id,
        @Schema(description = "Author's name", example = "Taras Shevchenko") String name,
        @Schema(description = "Additional info") String note,
        @Schema(description = "Genre") GenreResponse genre
) {}

