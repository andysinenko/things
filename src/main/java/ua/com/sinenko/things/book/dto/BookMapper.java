package ua.com.sinenko.things.book.dto;

import org.springframework.data.domain.Page;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.entity.Series;
import ua.com.sinenko.things.place.dto.PlaceMapper;
import ua.com.sinenko.things.place.entity.Place;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class BookMapper {
    private final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

    public static Book dtoToEntity(BookRequest request, List<Author> authors, Genre genre, Series series, Place place) {
        if (request != null)
            return Book.builder()
                    .title(request.title())
                    .authors(authors)
                    .genre(genre)
                    .series(series)
                    .place(place)
                    .year(request.year())
                    .description(request.description())
                    .volumeNumber(request.volume())
                    .build();
        return null;
    }

    public static BookResponse entityToDto(Book entity) {
        if(entity != null)
            return BookResponse
                .builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .genre(GenreMapper.entityToDto(entity.getGenre()))
                    .authors(AuthorMapper.entitiesToResponses(entity.getAuthors()))
                    .place(PlaceMapper.entityToResponse(entity.getPlace()))
                    .year(entity.getYear().toString())
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
                .place(PlaceMapper.entityToResponse(entity.getPlace()))
                .build();
        return null;
    }

    public static BookPageResponse entityToResponse(Page<Book> pageEntity) {
            return BookPageResponse.builder()
                .books(pageEntity.getContent().stream().map(BookMapper::entityToResponse).toList())
                .pageNumber(pageEntity.getNumber())
                .pageSize(pageEntity.getSize())
                .total(pageEntity.getTotalPages())
                .build();
    }

    private static List<AuthorResponse> getAuthorsDto(List<Author> authors) {
        if(authors == null) return Collections.emptyList();
        return authors.stream().map(e -> AuthorMapper.entityToResponse(e)).toList();
    }
}
