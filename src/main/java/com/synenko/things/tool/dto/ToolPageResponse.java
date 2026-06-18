package com.synenko.things.tool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(name = "ToolPageResponse", description = "Tool pagable response object")
public record ToolPageResponse(
        @Schema(name = "tools", description = "List of the tools")
        List<ToolResponse> tools,
        @Schema(name = "pageNumber", description = "Current page")
        int pageNumber,
        @Schema(name = "pageSize", description = "Size of page for pagination")
        int pageSize,
        @Schema(name = "total", description = "Total quantity of records")
        int total
        ) {}
