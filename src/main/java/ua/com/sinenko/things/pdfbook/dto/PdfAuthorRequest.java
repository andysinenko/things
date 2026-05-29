package ua.com.sinenko.things.pdfbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Request object for create a new pdf book")
public record PdfAuthorRequest(
        @Schema(description = "name of author")
        String name) { }
