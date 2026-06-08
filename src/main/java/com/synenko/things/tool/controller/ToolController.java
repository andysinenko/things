package com.synenko.things.tool.controller;

import com.synenko.things.tool.dto.ToolRequest;
import com.synenko.things.tool.dto.ToolResponse;
import com.synenko.things.tool.dto.VendorMapper;
import com.synenko.things.tool.dto.VendorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.synenko.things.tool.dto.*;
import com.synenko.things.tool.service.ToolService;
import com.synenko.things.tool.service.VendorService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tools")
@RequiredArgsConstructor
@Tag(name = "Tools controller", description = "Operations with tools")
public class ToolController {
    private static final Logger logger = LoggerFactory.getLogger(ToolController.class);

    private final ToolService toolService;
    private final VendorService vendorService;


    @Operation(summary = "List of tools", description = "Return list of tools")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<List<ToolResponse>> getAllTools() {
        return ResponseEntity.ok(toolService.getAllTools());
    }

    @Operation(summary = "New book", description = "Create a new book")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Tool dto",
            required = true,
            content = @Content(schema = @Schema(implementation = ToolRequest.class)))
    @ApiResponse(responseCode = "201", description = "Tool created successfully")
    @PostMapping
    public ResponseEntity<Void> addTool(@RequestBody ToolRequest toolRequest) {
        var toolResponse = toolService.saveTool(toolRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toolResponse.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Change tool", description = "Change tool")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Tool dto",
            required = true,
            content = @Content(schema = @Schema(implementation = ToolRequest.class)))
    @ApiResponse(responseCode = "201", description = "Book changed successfully")
    @PutMapping("/{id}")
    public ResponseEntity<ToolResponse> updateTool(@Parameter(description = "tool's id", example = "54", required = true) @PathVariable Long id, @RequestBody ToolRequest toolRequest) {
        var toolResponse = toolService.updateTool(id, toolRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(toolResponse.id())
                .toUri();

        return ResponseEntity.created(location).body(toolResponse);
    }

    @Operation(summary = "Get tool by id", description = "Returns a single tool by its id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved tool",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ToolResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tool not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ToolResponse> getOne(
            @Parameter(description = "tool id", example = "42", required = true)
            @PathVariable("id") String id) {
        //todo implement!
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete tool by id", description = "Delete a tool")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Tool deleted successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tool not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToolById(
            @Parameter(description = "tool id", example = "43", required = true)
            @PathVariable("id") Long id) {
        logger.info("Deleting tool with id {}", id);
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "List of brands", description = "Return list of brands")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved brands",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VendorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping(value = "/brands", produces = "application/json")
    public ResponseEntity<List<VendorResponse>> getToolBrands() {
        logger.info("Getting brands");
        var vendors = vendorService.getAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }


}
