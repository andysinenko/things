package ua.com.sinenko.things.book.dto;

import lombok.Builder;

import java.util.Date;
import java.util.Set;

@Builder
public record BookDto(
    Long id,
    String title,
    Set<AuthorDto> authors,
    GenreDto genre,
    SeriesDto series,
    String publisher,
    Date year,
    String placeId,
    String volumeNumber,
    String description) {}
