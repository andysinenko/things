package ua.com.sinenko.things.book.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record BookPageResponse(
        List<BookResponse> books,
        int pageNumber,
        int pageSize,
        int total
        ) {}
