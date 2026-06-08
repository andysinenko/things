package com.synenko.things.place.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.synenko.things.place.entity.Place;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Place Mapper Test")
class PlaceMapperTest {
    @Test
    @DisplayName("Map Entity 2 DTO Test")
    void requestToEntity() {
        //given
        var placeDto = PlaceRequest.builder()
                .name("test")
                .description("test")
                .parent(1L)
                .build();

        //when
        var place = PlaceMapper.requestToEntity(placeDto, Place.builder()
                .id(1L)
                .name("test")
                .description("test")
                .parent(null)
                .build());

        //then
        assertNotNull(place);
        assertEquals(placeDto.name(), place.getName());
        assertEquals(placeDto.description(), place.getDescription());
    }

    @DisplayName("Map DTO 2 Entity Test")
    @Test
    void entityToResponse() {
        //given
        var place = Place.builder()
                .id(1L)
                .name("test")
                .description("test")
                .parent(Place.builder().id(2L).build())
                .build();

        //when
        var placeDto = PlaceMapper.entityToResponse(place);

        //then
        assertNotNull(placeDto);
        assertEquals(place.getId(), placeDto.id());
        assertEquals(place.getName(), placeDto.name());
        assertEquals(place.getDescription(), placeDto.description());
        assertEquals(place.getParent().getId(), placeDto.parent().id());
    }
}