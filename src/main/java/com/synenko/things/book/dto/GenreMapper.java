package com.synenko.things.book.dto;

import com.synenko.things.book.entity.Genre;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreMapper {
    public static GenreResponse entityToDto(Genre entity) {
        if(entity != null)
            return GenreResponse
                    .builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        return null;
    }

    public static Genre dtoToEntity(GenreRequest dto) {
        if(dto != null)
            return Genre
                .builder()
                .name(dto.name())
                .build();
        return null;
    }

    public static Collection<GenreResponse> entitiesToDtos(Collection<Genre> entities) {
        if(entities != null && entities.size() != 0)
            return entities.stream()
                .map(e -> entityToDto(e))
                .collect(Collectors.toCollection(() -> Set.of()));
        return null;
    }

    public static Collection<Genre> dtosToEntities(Collection<GenreRequest> dtos) {
        if(dtos != null && dtos.size() > 0)
            return dtos.stream()
                .map(e -> dtoToEntity(e))
                .collect(Collectors.toCollection(() -> Set.of()));
        return null;
    }
}
