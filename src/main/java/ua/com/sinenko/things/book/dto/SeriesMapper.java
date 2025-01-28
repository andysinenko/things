package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Series;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SeriesMapper {
    public static SeriesDto mapEntityToDto(Series entity) {
        return SeriesDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static Series mapDtoToEntity(SeriesDto dto) {
        return Series
                .builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    public static Collection<SeriesDto> mapEntitiesToDtos(Collection<Series> entities) {
        return entities.stream()
                .map(e -> mapEntityToDto(e))
                .collect(Collectors.toCollection(() -> Set.of()));
    }

    public static Collection<Series> mapDtosToEntities(Collection<SeriesDto> dto) {
        return dto.stream()
                .map(e -> mapDtoToEntity(e))
                .collect(Collectors.toCollection(() -> Set.of()));
    }
}
