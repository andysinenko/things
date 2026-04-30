package ua.com.sinenko.things.book.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Series controller", description = "Operations with series of the books")
public class SeriesController {

    private SeriesService seriesService;

    @Operation(
            summary = "Get all series",
            description = "Return all book's series"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SeriesDto.class)
            )
    )
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<SeriesDto>> getAllSeries() {
        var seriesList = seriesService.getAllSeries();
        var seriesDtos = seriesList.stream().map(SeriesMapper::entityToDto).toList();

        return new ResponseEntity<>(seriesDtos, HttpStatus.OK);
    }
}
