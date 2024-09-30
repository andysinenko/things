package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.entity.Book;

public class BookMapper {
    public static Book mapDtoToEntity(BookDto dto) {
        return Book
                .builder()
                .id(dto.id())
                .genre(dto.genre())
                .series(dto.series())
                .year(dto.year())
                .name(dto.name())
                .description(dto.description())
                .volumeNumber(dto.volumeNumber())
                .authors(AuthorMapper.mapDtosToEntities(dto.authors()))
                .build();
    }

    public static BookDto mapEntityToDto(Book entity) {
        return BookDto
                .builder()
                .id(entity.getId())
                .genre(entity.getGenre())
                .series(entity.getSeries())
                .year(entity.getYear())
                .name(entity.getName())
                .description(entity.getDescription())
                .volumeNumber(entity.getVolumeNumber())
                .authors(AuthorMapper.mapEntitiesToDtos(entity.getAuthors()))
                .build();
    }
}
