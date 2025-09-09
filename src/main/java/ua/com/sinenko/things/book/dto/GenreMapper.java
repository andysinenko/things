package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Genre;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreMapper {
    public static GenreDto entityToDto(Genre entity) {
        if(entity != null)
            return GenreDto
                    .builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .build();
        return null;
    }

    public static Genre dtoToEntity(GenreDto dto) {
        if(dto != null)
            return Genre
                .builder()
                .id(dto.id())
                .name(dto.name())
                .build();
        return null;
    }

    public static Collection<GenreDto> entitiesToDtos(Collection<Genre> entities) {
        if(entities != null && entities.size() != 0)
            return entities.stream()
                .map(e -> entityToDto(e))
                .collect(Collectors.toCollection(() -> Set.of()));
        return null;
    }

    public static Collection<Genre> dtosToEntities(Collection<GenreDto> dtos) {
        if(dtos != null && dtos.size() > 0)
            return dtos.stream()
                .map(e -> dtoToEntity(e))
                .collect(Collectors.toCollection(() -> Set.of()));
        return null;
    }
}
