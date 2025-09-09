package ua.com.sinenko.things.place.dto;

import ua.com.sinenko.things.place.entity.Place;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceMapper {
    public static Place dtoToEntity(PlaceDto dto) {
        if (dto == null) return null;
        return Place.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .level(dto.level())
                .parent(PlaceMapper.dtoToEntity(dto.parent()))
                .build();
    }

    public static PlaceDto entityToDto(Place entity) {
        if (entity == null) return null;
        return PlaceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .level(entity.getLevel())
                .parent(PlaceMapper.entityToDto(entity.getParent()))
                .build();
    }

    public static List<PlaceDto> entitiesToDtos(List<Place> entity) {
        return entity.stream()
                .map(e -> PlaceMapper.entityToDto(e))
                .collect(Collectors.toList());
    }

    public static List<Place> dtosToEntities(List<PlaceDto> dto) {
        return dto.stream()
                .map(e -> PlaceMapper.dtoToEntity(e))
                .collect(Collectors.toList());
    }
}
