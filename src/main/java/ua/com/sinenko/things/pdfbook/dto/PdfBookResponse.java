package ua.com.sinenko.things.pdfbook.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PdfBookResponse(
        String id,
        String title,
        CategoryDto category,
        PdfAuthorDto author,
        String yearOfRelease,
        String language,
        Integer numberOfPages,
        String content
        ) {}