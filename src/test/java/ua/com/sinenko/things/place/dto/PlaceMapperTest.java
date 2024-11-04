package ua.com.sinenko.things.place.dto;

import org.junit.jupiter.api.Test;
import ua.com.sinenko.things.place.entity.Place;

import static org.junit.jupiter.api.Assertions.*;

class PlaceMapperTest {
    @Test
    void mapDtoToEntity() {
        //given
        var placeDto = PlaceDto.builder()
                .id(1L)
                .name("test")
                .description("test")
                .parent(PlaceDto.builder().id(2L).name("parent_1_level").description("parent 1st level").build())
                .build();

        //when
        var place = PlaceMapper.mapDtoToEntity(placeDto);

        //then
        assertNotNull(place);
        assertEquals(placeDto.id(), place.getId());
        assertEquals(placeDto.name(), place.getName());
        assertEquals(placeDto.description(), place.getDescription());
        assertEquals(placeDto.parent().id(), place.getParent().getId());
    }

    @Test
    void mapEntityToDto() {
        //given
        var place = Place.builder()
                .id(1L)
                .name("test")
                .description("test")
                .parent(Place.builder().id(2L).build())
                .build();

        //when
        var placeDto = PlaceMapper.mapEntityToDto(place);

        //then
        assertNotNull(placeDto);
        assertEquals(place.getId(), placeDto.id());
        assertEquals(place.getName(), placeDto.name());
        assertEquals(place.getDescription(), placeDto.description());
        assertEquals(place.getParent().getId(), placeDto.parent().id());
    }
}