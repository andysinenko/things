package com.synenko.things.pdfbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Reponse for PDF book object")
@Builder
public record PdfBookResponse(
        @Schema(description = "id of pdf book", example = "43")
        Long id,
        @Schema(description = "title of pdf book", example = "C++ in 21 days")
        String title,
        @Schema(description = "category of pdf book")
        CategoryResponse category,
        @Schema(description = "author of pdf book")
        PdfAuthorResponse author,
        @Schema(description = "year of issue", example = "1999")
        String yearOfRelease,
        @Schema(description = "language of pdf book", example = "english")
        String language,
        @Schema(description = "number of pages", example = "256")
        Integer numberOfPages
) {}
