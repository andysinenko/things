package ua.com.sinenko.things.tool.web;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> addNewTool(ToolDto toolDto) {
        return new ResponseEntity<String>("new tool", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> getToolById(@PathVariable("id") String id) {
        return new ResponseEntity<>("tool " + id, HttpStatus.OK);
    }

    @GetMapping(value = "/brands", produces = "application/json")
    public ResponseEntity<List<VendorDto>> getToolBrands() {
        var vendors = vendorService.getAllVendors();
        var vendorDtos = VendorMapper.toVendorDtoList(vendors);
        return new ResponseEntity<>(vendorDtos, HttpStatus.OK);
    }


}
