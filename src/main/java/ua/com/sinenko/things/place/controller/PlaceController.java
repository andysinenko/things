package ua.com.sinenko.things.place.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.place.dto.PlaceMapper;
import ua.com.sinenko.things.place.dto.PlaceRequest;
import ua.com.sinenko.things.place.dto.PlaceResponse;
import ua.com.sinenko.things.place.service.PlaceService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<PlaceResponse>> getAllPlaces() {
        var places = placeService.getAllPlaces();
        var placesDto = PlaceMapper.entitiesToResponse(places);
        return new ResponseEntity<>(placesDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<List<PlaceResponse>> getPlaceById(@PathVariable("id") Long id) {
        var place = placeService.getPlaceById(id);
        var placeDto = PlaceMapper.entityToResponse(place);
        return new ResponseEntity<>(List.of(placeDto), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addNewPlace(@RequestBody PlaceRequest placeRequest) {
        placeService.savePlace(placeRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updatePlace(@RequestBody PlaceRequest placeRequest, @PathVariable long id) {
        placeService.updatePlace(placeRequest, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
