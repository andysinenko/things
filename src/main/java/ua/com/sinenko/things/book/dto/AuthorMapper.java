package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Author;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthorMapper {
    public static Author mapDtoToEntity(AuthorDto dto) {
        return Author.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    public static AuthorDto mapEntityToDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
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


    public static Author mapRequestToEntity(AuthorRequest authorRequest) {
        return Author.builder()
                .name(authorRequest.name())
                .note(authorRequest.note())
                .build();
    }

}
