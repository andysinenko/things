package ua.com.sinenko.things.book.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record BookDto(
    Long id,
    String title,
    Set<AuthorDto> authors,
    GenreDto genre,
    SeriesDto series,
    String publisher,
    String year,
    String placeId,
    String volumeNumber,
    String description) {}
