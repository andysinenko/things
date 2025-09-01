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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Book Mapper Test")
class BookMapperTest {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    @Test
    void mapDtoToEntity() throws ParseException {
        //given
        var authorDto = AuthorDto.builder()
                .id(1L)
                .name("Author Name")
                .build();
        var bookDto = BookDto.builder()
                .id(1L)
                .title("Book Name")
                .genre(1L)
                .series(1L)
                .year("2021")
                .description("Description")
                .volume("1")
                .author(List.of(authorDto))
                .build();

        //when
        var book = BookMapper.mapDtoToEntity(bookDto);

        //then
        assertNotNull(book);
        assertEquals(bookDto.id(), book.getId());
        assertEquals(bookDto.title(), book.getTitle());
        assertEquals(bookDto.genre(), book.getGenre().getId());
        assertEquals(bookDto.series(), book.getSeries().getId());
        assertEquals(bookDto.year() + "-01-01", book.getYear().toString());
        assertEquals(bookDto.description(), book.getDescription());
        assertEquals(bookDto.volume(), book.getVolumeNumber());
        assertEquals(1L, bookDto.author());
    }

    @Test
    void mapEntityToDto() throws ParseException {
        //given
        var book = Book.builder()
                .id(1L)
                .title("Book Name")
                .genre(Genre.builder().id(1L).name("Fiction").build())
                .series(Series.builder().id(1L).name("Series Name").build())
                .year(LocalDate.parse("2021-01-01"))
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
        assertEquals(book.getGenre().getId(), bookDto.genre());
        assertEquals(book.getSeries().getId(), bookDto.id());
        assertEquals(book.getYear(), LocalDate.parse(bookDto.year()));
        assertEquals(book.getDescription(), bookDto.description());
        assertEquals(book.getVolumeNumber(), bookDto.volume());
        assertEquals(book.getAuthors().size(), 1);
    }

}