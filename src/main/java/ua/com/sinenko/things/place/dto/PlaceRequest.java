package ua.com.sinenko.things.place.dto;

import lombok.Builder;

@Builder
public record PlaceRequest(
    String name,
    Long parent,
    Long level,
    String description
) {}
