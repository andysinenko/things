package ua.com.sinenko.things.tool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.sinenko.things.tool.entity.Tool;
import ua.com.sinenko.things.tool.entity.ToolType;
import ua.com.sinenko.things.tool.repository.ToolsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolsRepository toolsRepository;

    public List<Tool> getAllTools() {
        return toolsRepository.findAll();
    }

    @Transactional
    public void saveTool(Tool tool) {
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
    public void updateTool(Tool tool) {
        toolsRepository.save(tool);
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
