package ua.com.sinenko.things.book.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.sinenko.things.book.entity.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Author Mapper Test")
class AuthorMapperTest {

    @Test
    void dtoToEntity() {
        //given
        var authorDto = AuthorDto.builder()
                .id(1L)
                .name("test")
                .build();

        //when
        var author = AuthorMapper.dtoToEntity(authorDto);

        //then
        assertNotNull(author);
        assertEquals(authorDto.id(), author.getId());
        assertEquals(authorDto.name(), author.getName());
    }

    @Test
    void entityToDto() {
        //given
        var author = Author.builder()
                .id(1L)
                .name("test")
                .build();

        //when
        var authorDto = AuthorMapper.entityToDto(author);

        //then
        assertNotNull(authorDto);
        assertEquals(author.getId(), authorDto.id());
        assertEquals(author.getName(), authorDto.name());
    }

    @Test
    void dtosToEntities() {
        //given
        var authorDto1 = AuthorDto.builder()
                .id(1L)
                .name("test1")
                .build();
        var authorDto2 = AuthorDto.builder()
                .id(2L)
                .name("test2")
                .build();

        var authorDtos = List.of(authorDto1, authorDto2);

        //when
        var authors = AuthorMapper.dtosToEntities(authorDtos);

        //then
        assertNotNull(authors);
        assertEquals(2, authors.size());
    }

    @Test
    void entitiesToDtos() {
        //given
        var author1 = Author.builder()
                .id(1L)
                .name("test1")
                .build();
        var author2 = Author.builder()
                .id(2L)
                .name("test2")
                .build();

        var authors = List.of(author1, author2);

        //when
        var authorDtos = AuthorMapper.entitiesToDtos(authors);

        //then
        assertNotNull(authorDtos);
        assertEquals(2, authorDtos.size());
    }


}