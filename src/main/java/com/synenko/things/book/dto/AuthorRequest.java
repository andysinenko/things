package com.synenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "AuthorRequest", description = "Author of the book")
public record AuthorRequest(
        @Schema(description = "Author name", example = "Taras Shevchenko") String name,
        @Schema(description = "Additional info") String note,
        @Schema(description = "Author genre", example = "Taras Shevchenko") Long genre_id
) {}

