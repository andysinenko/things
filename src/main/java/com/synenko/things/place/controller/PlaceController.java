package com.synenko.things.place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.synenko.things.place.dto.PlaceMapper;
import com.synenko.things.place.dto.PlaceRequest;
import com.synenko.things.place.dto.PlaceResponse;
import com.synenko.things.place.service.PlaceService;

import java.util.List;

@Tag(name = "Places controller", description = "Operations with places")
@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @Operation(summary = "Get all places", description = "Returns a list of all places")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of places",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlaceResponse.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<PlaceResponse>> getAllPlaces() {
        var places = placeService.getAllPlaces();
        var placesDto = PlaceMapper.entitiesToResponse(places);
        return new ResponseEntity<>(placesDto, HttpStatus.OK);
    }

    @Operation(summary = "Get place by id", description = "Returns a single place by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved place",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlaceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Place not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<PlaceResponse>> getPlaceById(@Parameter(description = "Place id", example = "62", required = true)  @PathVariable("id") Long id) {
        var place = placeService.getPlaceById(id);
        var placeDto = PlaceMapper.entityToResponse(place);
        return new ResponseEntity<>(List.of(placeDto), HttpStatus.OK);
    }

    @Operation(summary = "New place", description = "Create a new place")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "PlaceRequest",
            required = true,
            content = @Content(schema = @Schema(implementation = PlaceRequest.class)))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Place created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlaceRequest.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<Void> addPlace(@RequestBody PlaceRequest placeRequest) {
        placeService.savePlace(placeRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update place by id", description = "Updates a place by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Place updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlaceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Place not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Place update request",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceRequest.class)
            )
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updatePlace(@RequestBody PlaceRequest placeRequest,
            @Parameter(description = "Place id", example = "64", required = true)
            @PathVariable long id) {
        placeService.updatePlace(placeRequest, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
