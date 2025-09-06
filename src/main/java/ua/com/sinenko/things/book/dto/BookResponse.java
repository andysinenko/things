package ua.com.sinenko.things.book.dto;

import lombok.Builder;
import ua.com.sinenko.things.place.dto.PlaceDto;

import java.util.List;

@Builder
public record BookResponse(
        Long id,
        String title,
        List<AuthorDto> authors,
        GenreDto genre,
        SeriesDto series,
        String year,
        PlaceDto place,
        String volume,
        String description) {}
