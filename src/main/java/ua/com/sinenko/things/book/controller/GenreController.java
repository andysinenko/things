package ua.com.sinenko.things.book.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.sinenko.things.book.dto.GenreDto;
import ua.com.sinenko.things.book.dto.GenreMapper;
import ua.com.sinenko.things.book.dto.SeriesDto;
import ua.com.sinenko.things.book.dto.SeriesMapper;
import ua.com.sinenko.things.book.service.GenreService;
import ua.com.sinenko.things.book.service.SeriesService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/genres")
@AllArgsConstructor
public class GenreController {

    private GenreService genreService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<GenreDto>> getAllSeries() {
        var genreList = genreService.getAllSeries();
        var genresDtos = genreList.stream().map(GenreMapper::mapEntityToDto).toList();

        return new ResponseEntity<>(genresDtos, HttpStatus.OK);
    }
}
