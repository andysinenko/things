package ua.com.sinenko.things.book.dto;

import lombok.Builder;

@Builder
public record AuthorDto(Long id, String authorName) {}

