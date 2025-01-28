package ua.com.sinenko.things.book.dto;

import lombok.Builder;

@Builder
public record GenreDto(Long id, String name) {
}

