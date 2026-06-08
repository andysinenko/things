package com.synenko.things.tool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.synenko.things.common.exception.PlaceNotExistsException;
import com.synenko.things.common.exception.SeriesNotExistsException;
import com.synenko.things.place.repository.PlaceRepository;
import com.synenko.things.tool.dto.ToolRequest;
import com.synenko.things.tool.dto.ToolMapper;
import com.synenko.things.tool.dto.ToolResponse;
import com.synenko.things.tool.entity.Tool;
import com.synenko.things.tool.entity.ToolType;
import com.synenko.things.tool.repository.ToolsRepository;
import com.synenko.things.tool.repository.VendorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {
    private final ToolsRepository toolsRepository;
    private final VendorRepository vendorRepository;
    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public List<ToolResponse> getAllTools() {
        return toolsRepository.findAll()
                .stream()
                .map(ToolMapper::entityToResponse)
                .toList();
    }

    @Transactional
    public ToolResponse saveTool(ToolRequest toolRequest) {
        var tool = getFullfilledToolEntity(toolRequest);
        tool = toolsRepository.save(tool);
        return  ToolMapper.entityToResponse(tool);
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
    public ToolResponse updateTool(Long id, ToolRequest toolRequest) {
        var tool = toolsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found"));

        var place = placeRepository.findById(toolRequest.place())
                .orElseThrow(() -> new PlaceNotExistsException(toolRequest.place()));

        var vendor = vendorRepository.findById(toolRequest.vendor())
                .orElseThrow(() -> new SeriesNotExistsException(toolRequest.vendor()));

        tool.setName(toolRequest.name());
        tool.setPlace(place);
        tool.setVendor(vendor);
        tool.setType(toolRequest.toolType());
        tool.setSerialNumber(toolRequest.serialNumber());
        tool.setDateOfPurchasing(toolRequest.dateOfPurchasing());
        tool.setDescription(toolRequest.description());

        return ToolMapper.entityToResponse(toolsRepository.save(tool));
    }

    @Transactional(readOnly = true)
    private Tool getFullfilledToolEntity(ToolRequest toolRequest) {
        var place = placeRepository.findById(toolRequest.place())
                .orElseThrow(() -> new PlaceNotExistsException(toolRequest.place()));

        var vendor = vendorRepository.findById(toolRequest.vendor())
                .orElseThrow(() -> new SeriesNotExistsException(toolRequest.vendor()));

        return ToolMapper.dtoToEntity(toolRequest, place, vendor);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public List<ToolResponse> getToolsByVendor(String vendorName) {
        var tools = toolsRepository.findToolsByVendor_Name(vendorName);
        return ToolMapper.entitiesToResponses(tools);
    }

    @Transactional(readOnly = true)
    public List<ToolResponse> getToolsByType(String type) {
        var tools = toolsRepository.findByType(ToolType.valueOf(type));
        return ToolMapper.entitiesToResponses(tools);
    }
}
