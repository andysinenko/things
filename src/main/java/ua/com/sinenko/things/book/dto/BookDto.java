package ua.com.sinenko.things.book.dto;

import lombok.Builder;
import ua.com.sinenko.things.place.dto.PlaceDto;

import java.time.LocalDate;
import java.util.List;


@Builder
public record BookDto(
    Long id,
    String title,
    List<AuthorDto> authors,
    GenreDto genre,
    SeriesDto series,
    LocalDate year,
    PlaceDto place,
    String volume,
    String description) {}
