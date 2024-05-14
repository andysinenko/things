package ua.com.sinenko.things.rest.dto;

public record ToolDto(
    String id,
    String name,
    String brand,
    String year,
    String placeId,
    String description) {}
