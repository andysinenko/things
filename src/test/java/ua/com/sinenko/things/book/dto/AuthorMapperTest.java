package ua.com.sinenko.things.book.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.sinenko.things.book.entity.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Author Mapper Test")
class AuthorMapperTest {

    @Test
    void requestToEntity() {
        //given
        var authorDto = AuthorRequest.builder()
                .name("test")
                .build();

        //when
        var author = AuthorMapper.requestToEntity(authorDto);

        //then
        assertNotNull(author);
        assertEquals(authorDto.name(), author.getName());
    }

    @Test
    void entityToResponse() {
        //given
        var author = Author.builder()
                .id(1L)
                .name("test")
                .build();

        //when
        var authorDto = AuthorMapper.entityToResponse(author);

        //then
        assertNotNull(authorDto);
        assertEquals(author.getId(), authorDto.id());
        assertEquals(author.getName(), authorDto.name());
    }

    @Test
    void requestsToEntities() {
        //given
        var authorDto1 = AuthorRequest.builder()
                .name("test1")
                .build();
        var authorDto2 = AuthorRequest.builder()
                .name("test2")
                .build();

        var authorDtos = List.of(authorDto1, authorDto2);

        //when
        var authors = AuthorMapper.requestsToEntities(authorDtos);

        //then
        assertNotNull(authors);
        assertEquals(2, authors.size());
    }

    @Test
    void entitiesToResponses() {
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
        var authorDtos = AuthorMapper.entitiesToResponses(authors);

        //then
        assertNotNull(authorDtos);
        assertEquals(2, authorDtos.size());
    }


}