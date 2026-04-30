package ua.com.sinenko.things.tool.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.tool.dto.*;
import ua.com.sinenko.things.tool.service.ToolService;
import ua.com.sinenko.things.tool.service.VendorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tools")
@RequiredArgsConstructor
@Tag(name = "Tools controller", description = "Operations with tools")
public class ToolController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolController.class);

    private final ToolService toolService;
    private final VendorService vendorService;


    @Operation(summary = "List of tools", description = "Return list of tools")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping
    public ResponseEntity<List<ToolResponse>> getAllTools() {
        LOGGER.info("get all tools");
        var tools = toolService.getAllTools();
        var toolsDto = ToolMapper.entitiesToResponses(tools);

        return new ResponseEntity<>(toolsDto, HttpStatus.OK);
    }

    @Operation(summary = "New book", description = "Create a new book")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Tool dto",
            required = true,
            content = @Content(schema = @Schema(implementation = ToolRequest.class)))
    @ApiResponse(responseCode = "201", description = "Tool created successfully")
    @PostMapping
    public ResponseEntity<Void> addTool(@RequestBody ToolRequest toolRequest) {
        toolService.saveTool(toolRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Change tool", description = "Change tool")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Tool dto",
            required = true,
            content = @Content(schema = @Schema(implementation = ToolRequest.class)))
    @ApiResponse(responseCode = "201", description = "Book changed successfully")
    @PutMapping("/{id}")
    public ResponseEntity<ToolResponse> updateTool(@Parameter(description = "tool's id", example = "54", required = true) @PathVariable Long id, @RequestBody ToolRequest toolRequest) {
        var tool = toolService.updateTool(id, toolRequest);
        var toolResponse = ToolMapper.entityToResponse(tool);
        return new ResponseEntity<>(toolResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolResponse> getToolById(@PathVariable("id") String id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToolById(@PathVariable("id") Long id) {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/brands", produces = "application/json")
    public ResponseEntity<List<VendorRequest>> getToolBrands() {
        var vendors = vendorService.getAllVendors();
        var vendorDtos = VendorMapper.entitiesToDtos(vendors);
        return new ResponseEntity<>(vendorDtos, HttpStatus.OK);
    }


}
