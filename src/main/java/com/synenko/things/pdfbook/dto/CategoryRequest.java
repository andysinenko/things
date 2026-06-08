package com.synenko.things.pdfbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Request object for create a new category of the book")
public record CategoryRequest(
        @Schema(description = "Category's name")
        String name) { }
