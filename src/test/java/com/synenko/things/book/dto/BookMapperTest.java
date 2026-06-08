package com.synenko.things.book.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.synenko.things.book.entity.Author;
import com.synenko.things.book.entity.Book;
import com.synenko.things.book.entity.Genre;
import com.synenko.things.book.entity.Series;
import com.synenko.things.place.entity.Place;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Book Mapper Test")
class BookMapperTest {

    @Test
    void dtoToEntity() throws ParseException {
        //given
        var bookRequest = BookRequest.builder()
                .title("Book Name")
                .series(1L)
                .genre(1L)
                .place(1L)
                .year(LocalDate.parse("2021-01-01"))
                .description("Description")
                .volume("1")
                .authors(List.of(1L))
                .build();

        //when
        var book = BookMapper.dtoToEntity(bookRequest,
                List.of(Author.builder().id(1L).name("name").note("note").build()),
                Genre.builder().id(1L).name("genre").build(),
                Series.builder().id(1L).name("series").build(),
                Place.builder().id(1L).name("place").build());

        //then
        assertNotNull(book);
        assertEquals(bookRequest.title(), book.getTitle());
        assertEquals(bookRequest.series(), book.getSeries().getId());
        assertEquals(bookRequest.place(), book.getPlace().getId());
        assertEquals(bookRequest.year(), book.getYear());
        assertEquals(bookRequest.description(), book.getDescription());
        assertEquals(bookRequest.volume(), book.getVolumeNumber());
    }

    @Test
    void entityToDto() throws ParseException {
        //given
        var book = Book.builder()
                .id(1L)
                .title("Book Name")
                .genre(Genre.builder().id(1L).name("Fiction").build())
                .series(Series.builder().id(1L).name("Series Name").build())
                .authors(List.of(Author.builder().id(1L).name("Author Name").build()))
                .year(LocalDate.parse("2021-01-01"))
                .description("Description")
                .volumeNumber("1")
                .build();

        //when
        var bookDto = BookMapper.entityToDto(book);

        //then
        assertNotNull(bookDto);
        assertEquals(book.getId(), bookDto.id());
        assertEquals(book.getTitle(), bookDto.title());
        assertEquals(book.getYear().toString(), bookDto.year());
        assertEquals(book.getDescription(), bookDto.description());
        assertEquals(book.getVolumeNumber(), bookDto.volume());
        assertEquals(1, book.getAuthors().size());
    }

    @Test
    void dtoToEntity_with_null_fields() throws ParseException {
        //given
        var bookRequest = BookRequest.builder()
                .title("Book Name")
                .genre(1L)
                .series(1L)
                .authors(List.of(1L))
                .year(LocalDate.parse("2021-01-01"))
                .description("Description")
                .volume("1")
                .build();

        //when
        var book = BookMapper.dtoToEntity(bookRequest,
                List.of(Author.builder().id(1L).name("Author Name").build()),
                Genre.builder().id(1L).name("Fiction").build(),
                Series.builder().id(1L).name("Series Name").build(),
                Place.builder().id(1L).name("Place Name").build()
        );

        //then
        assertNotNull(book);
        assertEquals(bookRequest.title(), book.getTitle());
        assertNotNull(book.getGenre());
        assertNotNull(book.getSeries());
        assertNotNull(bookRequest.authors());
        assertEquals(bookRequest.year(), book.getYear());
        assertEquals(bookRequest.description(), book.getDescription());
        assertEquals(bookRequest.volume(), book.getVolumeNumber());
    }

    @Test
    void entityToDto_with_null_fields() {
        //given
        var book = Book.builder()
                .id(1L)
                .title("Book Name")
                .genre(null)
                .series(null)
                .authors(null)
                .place(null)
                .year(LocalDate.parse("2021-01-01"))
                .description("Description")
                .volumeNumber("1")
                .build();

        //when
        var bookResponse = BookMapper.entityToDto(book);

        //then
        assertNotNull(bookResponse);
        assertEquals(book.getId(), bookResponse.id());
        assertEquals(book.getTitle(), bookResponse.title());
        assertNull(bookResponse.genre());
        assertNull(bookResponse.series());
        assertNull(bookResponse.authors());
        assertNull(bookResponse.place());
        assertEquals(book.getYear().toString(), bookResponse.year());
        assertEquals(book.getDescription(), bookResponse.description());
        assertEquals(book.getVolumeNumber(), bookResponse.volume());
    }
}