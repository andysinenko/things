package com.synenko.things.pdfbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Request update of PDF book")
@Builder
public record PdfBookRequest(
        @Schema(description = "a new title of pdf book", example = "C++ in 21 days")
        String title,
        @Schema(description = "category id")
        Long category,
        @Schema(description = "author id")
        Long author,
        @Schema(description = "year of issue", example = "1999")
        String yearOfRelease,
        @Schema(description = "language of pdf book", example = "english")
        String language,
        @Schema(description = "number of pages", example = "256")
        Integer numberOfPages
) {}
