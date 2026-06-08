package com.synenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "AuthorDto", description = "Author of the book")
public record AuthorResponse(
        @Schema(name ="id", description = "Author id", example = "1") Long id,
        @Schema(name ="name", description = "Author's name", example = "Taras Shevchenko") String name,
        @Schema(name = "note", description = "Additional info") String note) {}

