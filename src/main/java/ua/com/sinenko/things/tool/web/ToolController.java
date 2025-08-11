package ua.com.sinenko.things.tool.web;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.sinenko.things.tool.dto.ToolDto;
import ua.com.sinenko.things.tool.dto.ToolMapper;
import ua.com.sinenko.things.tool.dto.VendorDto;
import ua.com.sinenko.things.tool.dto.VendorMapper;
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
    public ResponseEntity<List<ToolDto>> getAllTools() {
        var tools = toolService.getAllTools();
        var toolsDto = ToolMapper.mapEntitiesToDtos(tools);
        return new ResponseEntity<List<ToolDto>>(toolsDto, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addNewTool(@RequestBody ToolDto toolDto) {
        LOGGER.debug("Add new tool: {}", toolDto);
        var tool = ToolMapper.mapDtoToEntity(toolDto);
        toolService.saveTool(tool);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> getToolById(@PathVariable("id") String id) {
        return new ResponseEntity<>("tool " + id, HttpStatus.OK);
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
