package com.synenko.things.pdfbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response object for author of the pdf book")
public record PdfAuthorResponse(
        @Schema(description = "pdf book author id")
        Long id,
        @Schema(description = "pdf book author name")
        String name) { }
