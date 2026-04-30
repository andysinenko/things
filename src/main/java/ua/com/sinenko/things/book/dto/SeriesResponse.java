package ua.com.sinenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name="SeriesDto", description = "Series dto object")
public record SeriesResponse(
        @Schema(name="id", description = "Id of the book's series")
        Long id,
        @Schema(name="name", description = "Name of the book's series")
        String name) {
}
