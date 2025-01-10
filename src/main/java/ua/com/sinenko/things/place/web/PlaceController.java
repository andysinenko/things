package ua.com.sinenko.things.place.web;

import jakarta.websocket.server.PathParam;
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
    public ResponseEntity<List<String>> getAllPlaces() {
        return new ResponseEntity<List<String>>(Arrays.asList("all places", "all places", "all places"), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addNewPlaces(@RequestBody PlaceDto placeDto) {
        return new ResponseEntity<String>("new place " + placeDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<List<PlaceDto>> getPlaceById(@PathParam("id") Long id) {
        var place = placeService.getPlaceById(id);
        var placeDto = PlaceMapper.mapEntityToDto(place);
        return new ResponseEntity<List<PlaceDto>>(List.of(placeDto), HttpStatus.OK);
    }
}
