package ua.com.sinenko.things.tool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.sinenko.things.common.exception.PlaceNotExistsException;
import ua.com.sinenko.things.common.exception.VendorNotExistsException;
import ua.com.sinenko.things.place.repository.PlaceRepository;
import ua.com.sinenko.things.tool.dto.ToolDto;
import ua.com.sinenko.things.tool.dto.ToolMapper;
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
    public void saveTool(ToolDto toolDto) {
        var tool = ToolMapper.dtoToEntity(toolDto);
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
        return toolsRepository.findById(id).get();
    }

    @Transactional
    public Tool updateTool(Long id, ToolDto toolDto) {
        toolsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found"));

        var tool = ToolMapper.dtoToEntity(toolDto);

        return toolsRepository.saveAndFlush(tool);
    }

    public List<Tool> getToolsByDescription(String description) {
        return toolsRepository.findByDescription(description);
    }

    public List<Tool> getToolsByName(String name) {
        return toolsRepository.findByName(name);
    }

    public List<Tool> getToolsByVendor(String vendorName) {
        return toolsRepository.findToolsByVendor_Name(vendorName);
    }

    public List<Tool> getToolsByType(String type) {
        return toolsRepository.findByType(ToolType.valueOf(type));
    }
}
