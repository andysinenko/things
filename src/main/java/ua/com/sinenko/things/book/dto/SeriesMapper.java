package ua.com.sinenko.things.book.dto;

import ua.com.sinenko.things.book.entity.Series;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SeriesMapper {
    public static SeriesResponse entityToDto(Series entity) {
        if(entity != null)
            return SeriesResponse
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
        return null;
    }

    public static Series maptoToEntity(SeriesRequest request) {
        if(request != null)
            return Series
                .builder()
                .name(request.name())
                .build();
        return null;
    }

    public static Collection<SeriesResponse> entitiesToDtos(Collection<Series> entities) {
        if(entities != null && entities.size() > 0)
            return entities.stream()
                .map(e -> entityToDto(e))
                .collect(Collectors.toCollection(() -> Set.of()));
        return null;
    }

    public static Collection<Series> dtosToEntities(Collection<SeriesRequest> dtos) {
        if(dtos != null && dtos.size() > 0)
            return dtos.stream()
                .map(e -> maptoToEntity(e))
                .collect(Collectors.toCollection(() -> Set.of()));
        return null;
    }
}
