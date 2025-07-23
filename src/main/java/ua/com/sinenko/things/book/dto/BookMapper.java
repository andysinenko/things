package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Book;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class BookMapper {
    public static Book mapDtoToEntity(BookDto dto) {
        return Book
                .builder()
                .id(dto.id())
                .title(dto.title())
                .authors(dto.author() != null ? dto.author().stream()
                        .map(authorDto -> AuthorMapper.mapDtoToEntity(authorDto)).collect(Collectors.toSet()) : null)
                .year(LocalDate.parse(dto.year()+ "-01-01"))
                .description(dto.description())
                .volumeNumber(dto.volumeNumber())
                .build();
    }

    public static BookDto mapEntityToDto(Book entity) {
        return BookDto
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .year(entity.getYear().toString())
                .description(entity.getDescription())
                .volumeNumber(entity.getVolumeNumber())
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


    public static BookResponse mapEntityToResponse(Book entity) {
        return BookResponse
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .year(entity.getYear().toString())
                .description(entity.getDescription())
                .volumeNumber(entity.getVolumeNumber())
                .genre(GenreMapper.mapEntityToDto(entity.getGenre()))
                .authors(entity.getAuthors().stream().map(e -> AuthorMapper.mapEntityToDto(e)).collect(Collectors.toSet()))
                .series(SeriesMapper.mapEntityToDto(entity.getSeries()))
                .build();
    }
}
