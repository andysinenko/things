package com.synenko.things.tool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import com.synenko.things.tool.entity.ToolType;

import java.time.LocalDate;


@Schema(description = "Object for operations with tool")
@Builder
public record ToolRequest(
        @Schema(name = "tool's name", description = "name of tool", example = "Saw Makita")
        String name,
        @Schema(name = "tool type", description = "type of tool", example = "Chain saw")
        ToolType toolType,
        @Schema(name = "serial number", description = "serial number of tool if it exists")
        String serialNumber,
        @Schema(name = "vendor", description = "Vendor produced the tool")
        long vendor,
        @Schema(name = "place", description = "place of storing")
        long place,
        @Schema(name = "purchasing date", description = "purchasing date for memories")
        LocalDate dateOfPurchasing,
        @Schema(name = "description", description = "description")
        String description) {
}
