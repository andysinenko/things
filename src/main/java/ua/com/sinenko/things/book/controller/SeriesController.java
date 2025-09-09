package ua.com.sinenko.things.book.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.sinenko.things.book.dto.SeriesDto;
import ua.com.sinenko.things.book.dto.SeriesMapper;
import ua.com.sinenko.things.book.service.SeriesService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
@AllArgsConstructor
public class SeriesController {

    private SeriesService seriesService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<SeriesDto>> getAllSeries() {
        var seriesList = seriesService.getAllSeries();
        var seriesDtos = seriesList.stream().map(SeriesMapper::entityToDto).toList();

        return new ResponseEntity<>(seriesDtos, HttpStatus.OK);
    }
}
