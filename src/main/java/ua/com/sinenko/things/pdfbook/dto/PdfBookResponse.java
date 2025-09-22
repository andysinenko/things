package ua.com.sinenko.things.pdfbook.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PdfBookResponse(
        String id,
        String title,
        CategoryDto category,
        List<PdfAuthorDto> authors,
        String yearOfRelease,
        String language,
        String content
        ) {}