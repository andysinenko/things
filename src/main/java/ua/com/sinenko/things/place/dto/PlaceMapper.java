package ua.com.sinenko.things.place.dto;

import ua.com.sinenko.things.place.entity.Place;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceMapper {
    public static Place mapDtoToEntity(PlaceDto dto) {
        if (dto == null) return null;
        return Place.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .level(dto.level())
                .parent(PlaceMapper.mapDtoToEntity(dto.parent()))
                .build();
    }

    public static PlaceDto mapEntityToDto(Place entity) {
        if (entity == null) return null;
        return PlaceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .level(entity.getLevel())
                .parent(PlaceMapper.mapEntityToDto(entity.getParent()))
                .build();
    }

    public static List<PlaceDto> mapEntitiesToDtos(List<Place> entity) {
        return entity.stream()
                .map(e -> PlaceMapper.mapEntityToDto(e))
                .collect(Collectors.toList());
    }

    public static List<Place> mapDtosToEntities(List<PlaceDto> dto) {
        return dto.stream()
                .map(e -> PlaceMapper.mapDtoToEntity(e))
                .collect(Collectors.toList());
    }
}
