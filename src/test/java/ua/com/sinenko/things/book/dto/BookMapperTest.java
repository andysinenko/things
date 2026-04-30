package ua.com.sinenko.things.book.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.sinenko.things.book.entity.Author;
import ua.com.sinenko.things.book.entity.Book;
import ua.com.sinenko.things.book.entity.Genre;
import ua.com.sinenko.things.book.entity.Series;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Book Mapper Test")
class BookMapperTest {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

    @Test
    void dtoToEntity() throws ParseException {
        //given
        var authorResponse = AuthorRequest.builder()
                .name("Author Name")
                .build();
        var bookRequest = BookRequest.builder()
                .title("Book Name")
                .series(new SeriesRequest("Series 1"))
                .year(LocalDate.parse("2021-01-01"))
                .description("Description")
                .volume("1")
                .authors(List.of(authorResponse))
                .build();

        //when
        var book = BookMapper.dtoToEntity(bookRequest);

        //then
        assertNotNull(book);
        assertEquals(bookRequest.title(), book.getTitle());
        assertEquals(bookRequest.series().name(), book.getSeries().getName());
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
                .year(LocalDate.parse("2021-01-01"))
                .description("Description")
                .volumeNumber("1")
                .authors(List.of(Author.builder().id(1L).name("Author Name").build()))
                .build();

        //when
        var bookDto = BookMapper.entityToDto(book);

        //then
        assertNotNull(bookDto);
        assertEquals(book.getId(), bookDto.id());
        assertEquals(book.getTitle(), bookDto.title());
        assertEquals(book.getSeries().getId(), bookDto.id());
        assertEquals(book.getYear(), bookDto.year());
        assertEquals(book.getDescription(), bookDto.description());
        assertEquals(book.getVolumeNumber(), bookDto.volume());
        assertEquals(book.getAuthors().size(), 1);
    }

    @Test
    void dtoToEntity_with_null_fields() throws ParseException {
        //given
        var bookRequest = BookRequest.builder()
                .title("Book Name")
                .genre(null)
                .series(null)
                .authors(null)
                .year(LocalDate.parse("2021-01-01"))
                .description("Description")
                .volume("1")
                .build();

        //when
        var book = BookMapper.dtoToEntity(bookRequest);

        //then
        assertNotNull(book);
        assertEquals(bookRequest.title(), book.getTitle());
        assertNull(book.getGenre());
        assertNull(book.getSeries());
        assertNull(bookRequest.authors());
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
        var bookDto = BookMapper.entityToDto(book);

        //then
        assertNotNull(bookDto);
        assertEquals(book.getId(), bookDto.id());
        assertEquals(book.getTitle(), bookDto.title());
        assertNull(bookDto.genre());
        assertNull(bookDto.series());
        assertNull(bookDto.authors());
        assertNull(bookDto.place());
        assertEquals(book.getYear(), bookDto.year());
        assertEquals(book.getDescription(), bookDto.description());
        assertEquals(book.getVolumeNumber(), bookDto.volume());
    }
}
