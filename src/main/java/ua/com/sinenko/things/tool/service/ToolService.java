package ua.com.sinenko.things.tool.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.sinenko.things.tool.entity.Tool;
import ua.com.sinenko.things.tool.repository.ToolsRepository;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ToolService {
    private ToolsRepository toolsRepository;

    public List<Tool> getAllTools() {
        return toolsRepository.findAll();
    }

    public void saveTool(Tool tool) {
        toolsRepository.save(tool);
    }

    public void deleteTool(Long id) {
        toolsRepository.deleteById(id);
    }

    public Tool getToolById(Long id) {
        return toolsRepository.findById(id).get();
    }

    public void updateTool(Tool tool) {
        toolsRepository.save(tool);
    }

    public List<Tool> getToolsByDescription(String description) {
        return toolsRepository.findByDescription(description);
    }

    public List<Tool> getToolsByName(String name) {
        return toolsRepository.findByName(name);
    }

    public List<Tool> getToolsByVendor(String vendor) {
        return toolsRepository.findByVendor(vendor);
    }

    public List<Tool> getToolsByType(String type) {
        return toolsRepository.findByType(type);
    }
}
