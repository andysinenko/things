package ua.com.sinenko.things.place.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.place.dto.PlaceDto;
import ua.com.sinenko.things.place.dto.PlaceMapper;
import ua.com.sinenko.things.place.service.PlaceService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1/places")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<PlaceDto>> getAllPlaces() {
        var places = placeService.getAllPlaces();
        var placesDto = PlaceMapper.mapEntitiesToDtos(places);
        return new ResponseEntity<>(placesDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<List<PlaceDto>> getPlaceById(@PathVariable("id") Long id) {
        var place = placeService.getPlaceById(id);
        var placeDto = PlaceMapper.mapEntityToDto(place);
        return new ResponseEntity<>(List.of(placeDto), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addNewPlace(@RequestBody PlaceDto placeDto) {
        var place = PlaceMapper.mapDtoToEntity(placeDto);
        placeService.savePlace(place);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    public ResponseEntity<Void> updatePlace(@RequestBody PlaceDto placeDto) {
        var place = PlaceMapper.mapDtoToEntity(placeDto);
        placeService.savePlace(place);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
