package ua.com.sinenko.things.place.dto;

import lombok.Builder;

@Builder
public record PlaceResponse(
    Long id,
    String name,
    PlaceResponse parent,
    Long level,
    String description
) {}
