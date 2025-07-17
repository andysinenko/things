package ua.com.sinenko.things.book.dto;

import lombok.Builder;

@Builder
public record AuthorRequest(String name, String note) {}

