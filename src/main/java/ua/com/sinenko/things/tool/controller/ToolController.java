package ua.com.sinenko.things.tool.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.common.exception.aop.ThLogger;
import ua.com.sinenko.things.tool.dto.*;
import ua.com.sinenko.things.tool.service.ToolService;
import ua.com.sinenko.things.tool.service.VendorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tools")
@RequiredArgsConstructor
public class ToolController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolController.class);

    private final ToolService toolService;
    private final VendorService vendorService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ToolResponse>> getAllTools() {
        var tools = toolService.getAllTools();
        var toolsDto = ToolMapper.mapEntitiesToDtos(tools);
        return new ResponseEntity<>(toolsDto, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addTool(@RequestBody ToolDto toolDto) {
        toolService.saveTool(toolDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ThLogger
    public ResponseEntity<ToolResponse> updateTool(@PathVariable Long id, @RequestBody ToolDto toolDto) {
        var tool = toolService.updateTool(id, toolDto);
        var toolResponse = ToolMapper.mapEntityToDto(tool);
        return new ResponseEntity<>(toolResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ToolResponse> getToolById(@PathVariable("id") String id) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteToolById(@PathVariable("id") Long id) {
        toolService.deleteTool(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/brands", produces = "application/json")
    public ResponseEntity<List<VendorDto>> getToolBrands() {
        var vendors = vendorService.getAllVendors();
        var vendorDtos = VendorMapper.toVendorDtoList(vendors);
        return new ResponseEntity<>(vendorDtos, HttpStatus.OK);
    }


}
