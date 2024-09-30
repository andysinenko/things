package ua.com.sinenko.things.book.dto;

import lombok.Builder;

import java.util.Date;
import java.util.Set;

@Builder
public record BookDto(
    Long id,
    String title,
    Set<AuthorDto> authors,
    String genre,
    String publisher,
    Date year,
    String placeId,
    String name,
    String series,
    String volumeNumber,
    String description) {}
