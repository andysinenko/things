package ua.com.sinenko.things.pdfbook.dto;

import lombok.Builder;
import ua.com.sinenko.things.book.dto.BookResponse;

import java.util.List;

@Builder
public record PdfBookPageResponse(
        List<PdfBookResponse> pdfbooks,
        int pageNumber,
        int pageSize,
        int total
        ) {}
