package com.synenko.things.tool.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Vendor dto", description = "Object for operations with vendor")
public record VendorRequest(
        @Schema(name = "tool name", description = "name of tool", example = "Saw Makita")
        String name) {
}
