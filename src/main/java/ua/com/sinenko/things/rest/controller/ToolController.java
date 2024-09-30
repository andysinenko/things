package ua.com.sinenko.things.rest.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.sinenko.things.tool.dto.ToolDto;
import ua.com.sinenko.things.tool.dto.ToolMapper;
import ua.com.sinenko.things.tool.service.ToolService;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/tools")
public class ToolController {

    private ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<ToolDto>> getAllTools() {
        var tools = toolService.getAllTools();
        var toolsDto = ToolMapper.mapEntitiesToDtos(tools);
        return new ResponseEntity<List<ToolDto>>(toolsDto, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addNewPlaces() {
        return new ResponseEntity<String>("new place", HttpStatus.OK);
    }

    @GetMapping("/{placeId}")
    @ResponseBody
    public ResponseEntity<String> getPlaceById(@PathParam("placeId") String placeId) {
        return new ResponseEntity<String>("place " + placeId, HttpStatus.OK);
    }
}
