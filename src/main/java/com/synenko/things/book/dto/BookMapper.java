package com.synenko.things.book.dto;

import org.springframework.data.domain.Page;
import com.synenko.things.book.entity.Author;
import com.synenko.things.book.entity.Book;
import com.synenko.things.book.entity.Genre;
import com.synenko.things.book.entity.Series;
import com.synenko.things.place.dto.PlaceMapper;
import com.synenko.things.place.entity.Place;

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

    public static BookPageResponse entityToPagebleResponse(Page<BookResponse> pageEntity) {
            return BookPageResponse.builder()
                .books(pageEntity.getContent())
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
