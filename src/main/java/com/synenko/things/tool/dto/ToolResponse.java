package com.synenko.things.tool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import com.synenko.things.place.dto.PlaceResponse;
import com.synenko.things.tool.entity.ToolType;

import java.time.LocalDate;


@Schema(description = "Tool response object")
@Builder
public record ToolResponse(
        @Schema(name = "tool id", description = "id of tool", example = "43")
        Long id,
        @Schema(name = "tool name", description = "name of tool", example = "Saw Makita")
        String name,
        @Schema(name = "tool type", description = "type of tool", example = "Chain saw")
        ToolType toolType,
        @Schema(name = "serial number", description = "serial number of tool if it exists")
        String serialNumber,
        @Schema(name = "vendor", description = "Vendor produced the tool")
        VendorResponse vendor,
        @Schema(name = "place", description = "place of storing")
        PlaceResponse place,
        @Schema(name = "purchasing date", description = "purchasing date for memories")
        LocalDate dateOfPurchasing,
        @Schema(name = "description", description = "description")
        String description) {
}
