package ua.com.sinenko.things.place.dto;

import ua.com.sinenko.things.place.entity.Place;

public class PlaceMapper {
    public static Place mapDtoToEntity(PlaceDto dto) {
        return Place.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .parent(PlaceMapper.mapDtoToEntity(dto.parent()))
                .build();
    }

    public static PlaceDto mapDtoToEntity(Place entity) {
        return PlaceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .parent(PlaceMapper.mapDtoToEntity(entity.getParent()))
                .build();
    }
}
