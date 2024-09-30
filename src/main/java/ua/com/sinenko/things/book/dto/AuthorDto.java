package ua.com.sinenko.things.book.dto;

import jakarta.persistence.Column;
import lombok.Builder;

@Builder
public record AuthorDto(Long id, String name, String note, String condition) {}

