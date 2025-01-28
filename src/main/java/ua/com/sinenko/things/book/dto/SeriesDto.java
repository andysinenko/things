package ua.com.sinenko.things.book.dto;

import lombok.Builder;

@Builder
public record SeriesDto( Long id, String name) {
}
