package com.synenko.things.place.dto;

import com.synenko.things.place.entity.Place;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceMapper {
    public static Place requestToEntity(PlaceRequest request, Place parent) {
        if (request == null) return null;
        return Place.builder()
                .name(request.name())
                .description(request.description())
                .level(request.level())
                .parent(parent)
                .build();
    }

    public static PlaceResponse entityToResponse(Place entity) {
        if (entity == null) return null;
        return PlaceResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .level(entity.getLevel())
                .parent(PlaceMapper.entityToResponse(entity.getParent()))
                .build();
    }

    public static List<PlaceResponse> entitiesToResponse(List<Place> entity) {
        return entity.stream()
                .map(e -> PlaceMapper.entityToResponse(e))
                .collect(Collectors.toList());
    }
}
