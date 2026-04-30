package ua.com.sinenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "AuthorRequest", description = "Author of the book")
public record AuthorRequest(
        @Schema(name = "name", description = "Author's name", example = "Taras Shevchenko") String name,
        @Schema(name = "note", description = "Additional info") String note) {}

