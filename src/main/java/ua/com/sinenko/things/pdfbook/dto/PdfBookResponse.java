package ua.com.sinenko.things.pdfbook.dto;

import lombok.Builder;

@Builder
public record PdfBookResponse(
        Long id,
        String title,
        CategoryResponse category,
        PdfAuthorResponse author,
        String yearOfRelease,
        String language,
        Integer numberOfPages
) {}