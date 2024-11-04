package ua.com.sinenko.things.place.dto;

import ua.com.sinenko.things.place.entity.Place;

public class PlaceMapper {
    public static Place mapDtoToEntity(PlaceDto dto) {
        if (dto == null) return null;
        return Place.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .parent(PlaceMapper.mapDtoToEntity(dto.parent()))
                .build();
    }

    public static PlaceDto mapEntityToDto(Place entity) {
        if (entity == null) return null;
        return PlaceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .parent(PlaceMapper.mapEntityToDto(entity.getParent()))
                .build();
    }
}
