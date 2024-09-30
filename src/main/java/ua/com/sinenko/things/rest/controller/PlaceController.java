package ua.com.sinenko.things.rest.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.place.dto.PlaceDto;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1/places")
public class PlaceController {

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

    @GetMapping(value = "/{placeId}")
    @ResponseBody
    public ResponseEntity<String> getPlaceById(@PathParam("placeId") String placeId) {
        return new ResponseEntity<String>("place " + placeId, HttpStatus.OK);
    }
}
