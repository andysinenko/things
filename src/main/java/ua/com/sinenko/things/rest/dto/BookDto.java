package ua.com.sinenko.things.rest.dto;

public record BookDto(
    String bookId,
    String title,
    String author,
    String genre,
    String publisher,
    String year,
    String placeId,
    String name,
    String series,
    String description) {}
