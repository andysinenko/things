package ua.com.sinenko.things.book.dto;

import org.junit.jupiter.api.Test;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.entity.Series;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    @Test
    void mapDtoToEntity() throws ParseException {
        //given
        var bookDto = BookDto.builder()
                .id(1L)
                .title("Book Name")
                .genre(new GenreDto(1L, "Fiction"))
                .series(new SeriesDto(1L, "Series Name"))
                .year(dateFormat.parse("2021"))
                .description("Description")
                .volumeNumber("1")
                .authors(Set.of(AuthorDto.builder().id(1L).name("Author Name").build()))
                .build();

        //when
        var book = BookMapper.mapDtoToEntity(bookDto);

        //then
        assertNotNull(book);
        assertEquals(bookDto.id(), book.getId());
        assertEquals(bookDto.title(), book.getTitle());
        assertEquals(bookDto.genre(), book.getGenre());
        assertEquals(bookDto.series(), book.getSeries());
        assertEquals(bookDto.year(), book.getYear());
        assertEquals(bookDto.description(), book.getDescription());
        assertEquals(bookDto.volumeNumber(), book.getVolumeNumber());
        assertEquals(bookDto.authors().size(), book.getAuthors().size());
    }

    @Test
    void mapEntityToDto() throws ParseException {
        //given
        var book = Book.builder()
                .id(1L)
                .title("Book Name")
                .genre(Genre.builder().id(1L).name("Fiction").build())
                .series(Series.builder().id(1L).name("Series Name").build())
                .year(dateFormat.parse("2021"))
                .description("Description")
                .volumeNumber("1")
                .authors(Set.of(Author.builder().id(1L).name("Author Name").build()))
                .build();

        //when
        var bookDto = BookMapper.mapEntityToDto(book);

        //then
        assertNotNull(bookDto);
        assertEquals(book.getId(), bookDto.id());
        assertEquals(book.getTitle(), bookDto.title());
        assertEquals(book.getGenre(), bookDto.genre());
        assertEquals(book.getSeries(), bookDto.series());
        assertEquals(book.getYear(), bookDto.year());
        assertEquals(book.getDescription(), bookDto.description());
        assertEquals(book.getVolumeNumber(), bookDto.volumeNumber());
        assertEquals(book.getAuthors().size(), bookDto.authors().size());
    }

}