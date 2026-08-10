package com.synenko.things.book.controller;

import com.synenko.things.book.dto.SeriesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synenko.things.book.dto.SeriesResponse;
import com.synenko.things.book.service.SeriesService;

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
                    schema = @Schema(implementation = SeriesResponse.class)
            )
    )
    @GetMapping
    public ResponseEntity<List<SeriesResponse>> getAllSeries() {
        return new ResponseEntity<>(seriesService.getAllSeries(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSeries(@RequestBody SeriesRequest seriesRequest, @PathVariable Long id) {
        seriesService.updateSeries(seriesRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeries(@PathVariable Long id) {
        seriesService.deleteSeries(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<SeriesResponse> createSeries(@RequestBody SeriesRequest seriesRequest) {
        var series = seriesService.saveSeries(seriesRequest);
        return new ResponseEntity<>(series, HttpStatus.CREATED);
    }
}

