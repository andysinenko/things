package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Book;

import java.util.Collection;
import java.util.stream.Collectors;

public class BookMapper {
    public static Book mapDtoToEntity(BookDto dto) {
        return Book
                .builder()
                .id(dto.id())
                .genre(GenreMapper.mapDtoToEntity(dto.genre()))
                .series(SeriesMapper.mapDtoToEntity(dto.series()))
                .year(dto.year())
                .description(dto.description())
                .volumeNumber(dto.volumeNumber())
                .authors(AuthorMapper.mapDtosToEntities(dto.authors()))
                .build();
    }

    public static BookDto mapEntityToDto(Book entity) {
        return BookDto
                .builder()
                .id(entity.getId())
                .genre(GenreMapper.mapEntityToDto(entity.getGenre()))
                .series(SeriesMapper.mapEntityToDto(entity.getSeries()))
                .year(entity.getYear())
                .description(entity.getDescription())
                .volumeNumber(entity.getVolumeNumber())
                .authors(AuthorMapper.mapEntitiesToDtos(entity.getAuthors()))
                .build();
    }


    public static Collection<Book> mapDtosToEntities(Collection<BookDto> dtos) {
        return dtos.stream()
                .map(dto -> mapDtoToEntity(dto))
                .collect(Collectors.toList());
    }

    public static Collection<BookDto> mapEntitiesToDtos(Collection<Book> entities) {
        return entities.stream()
                .map(entity -> mapEntityToDto(entity))
                .collect(Collectors.toList());
    }
}
