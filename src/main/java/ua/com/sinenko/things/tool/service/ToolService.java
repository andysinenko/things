package ua.com.sinenko.things.tool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.sinenko.things.place.repository.PlaceRepository;
import ua.com.sinenko.things.tool.dto.ToolRequest;
import ua.com.sinenko.things.tool.dto.ToolMapper;
import ua.com.sinenko.things.tool.dto.ToolResponse;
import ua.com.sinenko.things.tool.entity.Tool;
import ua.com.sinenko.things.tool.entity.ToolType;
import ua.com.sinenko.things.tool.repository.ToolsRepository;
import ua.com.sinenko.things.tool.repository.VendorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolsRepository toolsRepository;
    private final VendorRepository vendorRepository;
    private final PlaceRepository placeRepository;

    public List<Tool> getAllTools() {
        return toolsRepository.findAll();
    }

    @Transactional
    public void saveTool(ToolRequest toolRequest) {
        var tool = ToolMapper.dtoToEntity(toolRequest);
        toolsRepository.save(tool);
    }

    @Transactional
    public void deleteTool(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Tool ID cannot be null");
        }
        if (!toolsRepository.existsById(id)) {
            throw new IllegalArgumentException("Tool with id " + id + " not found");
        }
        toolsRepository.deleteById(id);
    }

    public Tool getToolById(Long id) {
        return toolsRepository.findById(id).orElseGet(null);
    }

    @Transactional
    public Tool updateTool(Long id, ToolRequest toolRequest) {
        toolsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found"));
        var tool = ToolMapper.dtoToEntity(toolRequest);

        return toolsRepository.saveAndFlush(tool);
    }

    public List<ToolResponse> getToolsByDescription(String description) {
        var toolEntity = toolsRepository.findByDescription(description);
        var toolResponses = ToolMapper.entitiesToResponses(toolEntity);
        return toolResponses;
    }

    public List<ToolResponse> getToolsByName(String name) {
        var toolEntity = toolsRepository.findByName(name);
        var toolResponses = ToolMapper.entitiesToResponses(toolEntity);
        return toolResponses;
    }

    public List<Tool> getToolsByVendor(String vendorName) {
        return toolsRepository.findToolsByVendor_Name(vendorName);
    }

    public List<Tool> getToolsByType(String type) {
        return toolsRepository.findByType(ToolType.valueOf(type));
    }
}
