package com.synenko.things.pdfbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response object for create a new category of the book")
public record CategoryResponse(
        @Schema(description = "Category's id")
        Long id,
        @Schema(description = "Category's name")
        String name) { }
