package ua.com.sinenko.things.book.dto;

import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record BookDto(
    Long id,
    String title,
    List<AuthorDto> author,
    Long genre,
    Long series,
    String year,
    Long placeId,
    String volumeNumber,
    String description) {}
