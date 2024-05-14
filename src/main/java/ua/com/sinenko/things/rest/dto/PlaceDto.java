package ua.com.sinenko.things.rest.dto;

public record PlaceDto(
    String id,
    String name,
    PlaceDto parent,
    String description) {}
