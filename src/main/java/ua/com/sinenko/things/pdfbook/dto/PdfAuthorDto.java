package ua.com.sinenko.things.pdfbook.dto;

import lombok.Builder;

@Builder
public record PdfAuthorDto(Long id, String name) { }
