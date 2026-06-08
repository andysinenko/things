package com.synenko.things.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Place dto object")
@Builder
public record PlaceRequest(
        @Schema(description = "Name of the place", example = "Kitchen in village house")
        String name,
        @Schema(description = "id of place highest level", example = "Village house")
        Long parent,
        @Schema(description = "level of nesting", example = "2 - means kitchen")
        Long level,
        @Schema(description = "additional information about place")
        String description
) {}
