package ua.com.sinenko.things.book.dto;

import org.springframework.data.domain.Page;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.place.dto.PlaceMapper;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    private final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

    public static Book dtoToEntity(BookDto dto) {
        if (dto != null)
            return Book.builder()
                    .id(dto.id())
                    .title(dto.title())
                    .authors(AuthorMapper.dtosToEntities(dto.authors()))
                    .genre(GenreMapper.dtoToEntity(dto.genre()))
                    .series(SeriesMapper.maptoToEntity(dto.series()))
                    .place(PlaceMapper.dtoToEntity(dto.place()))
                    .year(dto.year())
                    .description(dto.description())
                    .volumeNumber(dto.volume())
                    .build();
        return null;
    }

    public static BookDto entityToDto(Book entity) {
        if(entity != null)
            return BookDto
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .genre(GenreMapper.entityToDto(entity.getGenre()))
                .authors(AuthorMapper.entitiesToDtos(entity.getAuthors()))
                .place(PlaceMapper.entityToDto(entity.getPlace()))
                .year(entity.getYear())
                .description(entity.getDescription())
                .volume(entity.getVolumeNumber())
                .build();
        return null;
    }


    public static BookResponse entityToResponse(Book entity) {
        if(entity != null)
            return BookResponse
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .year(entity.getYear().toString())
                .description(entity.getDescription())
                .volume(entity.getVolumeNumber())
                .genre(GenreMapper.entityToDto(entity.getGenre()))
                .authors(getAuthorsDto(entity.getAuthors()))
                .series(SeriesMapper.entityToDto(entity.getSeries()))
                .place(PlaceMapper.entityToDto(entity.getPlace()))
                .build();
        return null;
    }

    public static BookPageResponse entityToResponse(Page<Book> pageEntity) {
        if(pageEntity.getContent() != null || pageEntity.getContent().size() > 0)
            return BookPageResponse.builder()
                .books(pageEntity.getContent().stream().map(e -> BookMapper.entityToResponse(e)).toList())
                .pageNumber(pageEntity.getNumber())
                .pageSize(pageEntity.getSize())
                .total(pageEntity.getTotalPages())
                .build();
        return null;
    }

    private static List<AuthorDto> getAuthorsDto(List<Author> authors) {
        if(authors == null) return null;
        return authors.stream().map(e -> AuthorMapper.entityToDto(e)).collect(Collectors.toList());
    }
}
