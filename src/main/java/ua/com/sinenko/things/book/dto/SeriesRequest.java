package ua.com.sinenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name="SeriesDto", description = "Series dto object")
public record SeriesRequest(
        @Schema(name="name", description = "Name of the book's series")
        String name) {
}
