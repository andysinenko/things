package ua.com.sinenko.things.place.dto;

import lombok.Builder;

@Builder
public record PlaceDto(
    Long id,
    String name,
    PlaceDto parent,
    String description) {}
