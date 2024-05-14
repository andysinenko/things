package ua.com.sinenko.things.rest.dto;

public record PlaceDto(
    String placeId,
    String name,
    PlaceDto parent,
    String description) {}
