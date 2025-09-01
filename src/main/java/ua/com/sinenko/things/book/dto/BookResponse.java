package ua.com.sinenko.things.book.dto;

import lombok.Builder;
import ua.com.sinenko.things.place.dto.PlaceDto;

import java.util.Set;

@Builder
public record BookResponse(
        Long id,
        String title,
        Set<AuthorDto> authors,
        GenreDto genre,
        SeriesDto series,
        String year,
        PlaceDto place,
        String volume,
        String description) {}
