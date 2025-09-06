package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Author;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {
    public static Author dtoToEntity(AuthorDto dto) {
        if(dto != null)
            return Author.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
        return null;
    }

    public static AuthorDto entityToDto(Author entity) {
        if(entity != null)
            return AuthorDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
        return null;
    }

    public static List<Author> dtosToEntities(List<AuthorDto> dto) {
        if(dto != null)
            return dto.stream()
                        .map(e -> dtoToEntity(e))
                                .collect(Collectors.toList());
        return null;
    }

    public static List<AuthorDto> entitiesToDtos(List<Author> entities) {
        if(entities != null)
            return entities.stream()
                .map(e -> entityToDto(e))
                .collect(Collectors.toList());
        return null;
    }


    public static Author requestToEntity(AuthorRequest authorRequest) {
        if(authorRequest != null)
            return Author.builder()
                    .name(authorRequest.name())
                    .note(authorRequest.note())
                    .build();
        return null;
    }

}
