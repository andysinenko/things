package com.synenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name="Genre Request", description = "Dto object for book's genre")
public record GenreRequest(
        @Schema(name="name", description = "name") String name,
        @Schema(name="note", description = "note") String note

) {
}

