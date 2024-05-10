package ua.com.sinenko.things.rest;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1/tools")
public class ToolsController {

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<String>> getAllPlaces() {
        return new ResponseEntity<List<String>>(Arrays.asList("all places", "all places", "all places"), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addNewPlaces() {
        return new ResponseEntity<String>("new place", HttpStatus.OK);
    }

    @GetMapping("/{placeId}")
    @ResponseBody
    public ResponseEntity<String> getPlaceById(@PathParam("placeId") String placeId) {
        return new ResponseEntity<String>("place " + placeId, HttpStatus.OK);
    }
}
