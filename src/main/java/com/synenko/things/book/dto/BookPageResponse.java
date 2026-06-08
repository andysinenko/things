package com.synenko.things.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "BookPageResponse", description = "Book Response object")
public record BookPageResponse(
        @Schema(name = "books", description = "List of the books")
        List<BookResponse> books,
        @Schema(name = "pageNumber", description = "Current page")
        int pageNumber,
        @Schema(name = "pageSize", description = "Size of page for pagination")
        int pageSize,
        @Schema(name = "total", description = "Total quantity of records")
        int total
        ) {}
