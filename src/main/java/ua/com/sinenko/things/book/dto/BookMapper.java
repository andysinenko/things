package ua.com.sinenko.things.book.dto;

import org.springframework.data.domain.Page;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.place.dto.PlaceMapper;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
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
                .volumeNumber(dto.volume())
                .build();
    }

    public static BookDto mapEntityToDto(Book entity) {
        return BookDto
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .year(entity.getYear().toString())
                .description(entity.getDescription())
                .volume(entity.getVolumeNumber())
                .build();
    }


    public static BookResponse mapEntityToResponse(Book entity) {
        return BookResponse
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .year(entity.getYear().toString())
                .description(entity.getDescription())
                .volume(entity.getVolumeNumber())
                .genre(GenreMapper.mapEntityToDto(entity.getGenre()))
                .authors(entity.getAuthors().stream().map(e -> AuthorMapper.mapEntityToDto(e)).collect(Collectors.toSet()))
                .series(SeriesMapper.mapEntityToDto(entity.getSeries()))
                .place(PlaceMapper.mapEntityToDto(entity.getPlace()))
                .build();
    }

    public static BookPageResponse mapEntityToResponse(Page<Book> pageEntity) {
        return BookPageResponse.builder()
                .books(pageEntity.getContent().stream().map(e -> BookMapper.mapEntityToResponse(e)).toList())
                .pageNumber(pageEntity.getNumber())
                .pageSize(pageEntity.getSize())
                .total(pageEntity.getTotalPages())
                .build();
    }
}
