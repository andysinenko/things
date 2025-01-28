package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Genre;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreMapper {
    public static GenreDto mapEntityToDto(Genre entity) {
        return GenreDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static Genre mapDtoToEntity(GenreDto dto) {
        return Genre
                .builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    public static Collection<GenreDto> mapEntitiesToDtos(Collection<Genre> entities) {
        return entities.stream()
                .map(e -> mapEntityToDto(e))
                .collect(Collectors.toCollection(() -> Set.of()));
    }

    public static Collection<Genre> mapDtosToEntities(Collection<GenreDto> dto) {
        return dto.stream()
                .map(e -> mapDtoToEntity(e))
                .collect(Collectors.toCollection(() -> Set.of()));
    }
}
