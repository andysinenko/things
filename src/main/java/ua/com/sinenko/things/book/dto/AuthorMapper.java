package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Author;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    public static AuthorResponse entityToResponse(Author entity) {
        if(entity != null)
            return AuthorResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        return null;
    }

    public static List<AuthorResponse> entitiesToResponses(List<Author> entities) {
        if(entities != null)
            return entities.stream()
                .map(e -> entityToResponse(e))
                .collect(Collectors.toList());
        return null;
    }

    public static Author requestToEntity(AuthorRequest authorRequest) {
        if(authorRequest != null) {
            Author author = new Author();
            author.setName(authorRequest.name());
            author.setNote(authorRequest.note());
            return author;
        }
        return null;
    }

    public static List<Author> requestsToEntities(List<AuthorRequest> request) {
        if(request != null)
            return request.stream()
                    .map(e -> AuthorMapper.requestToEntity(e))
                    .collect(Collectors.toList());
        return null;
    }
}
