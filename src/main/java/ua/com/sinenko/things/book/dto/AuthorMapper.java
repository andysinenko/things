package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Author;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthorMapper {
    public static Author mapDtoToEntity(AuthorDto dto) {
        return Author.builder()
                .id(dto.id())
                .authorName(dto.authorName())
                .build();
    }

    public static AuthorDto mapEntityToDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .authorName(author.getAuthorName())
                .build();
    }

    public static Set<Author> mapDtosToEntities(Set<AuthorDto> dto) {
        return dto.stream()
                        .map(e -> mapDtoToEntity(e))
                                .collect(Collectors.toSet());
    }

    public static Set<AuthorDto> mapEntitiesToDtos(Set<Author> entities) {
        return entities.stream()
                .map(e -> mapEntityToDto(e))
                .collect(Collectors.toSet());
    }
}
