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
@RequestMapping("/api/v1/books")
public class BookController {

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<String>> getAllBooks() {
        return new ResponseEntity<List<String>>(Arrays.asList("all places", "all places", "all places"), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addNewBook(@RequestBody PlaceDto placeDto) {
        return new ResponseEntity<String>("new place " + placeDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{bookId}")
    @ResponseBody
    public ResponseEntity<String> getBookById(@PathParam("id") String id) {
        return new ResponseEntity<String>("place " + id, HttpStatus.OK);
    }
}
