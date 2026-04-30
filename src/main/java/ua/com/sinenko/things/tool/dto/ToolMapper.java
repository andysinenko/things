package ua.com.sinenko.things.tool.dto;

import ua.com.sinenko.things.place.dto.PlaceMapper;
import ua.com.sinenko.things.place.entity.Place;
import ua.com.sinenko.things.tool.entity.Tool;
import ua.com.sinenko.things.tool.entity.Vendor;

import java.util.List;
import java.util.stream.Collectors;

public class ToolMapper {
    public static Tool dtoToEntity(ToolRequest dto, Place place, Vendor vendor) {
        if (dto == null) return null;
        return Tool.builder()
                .name(dto.name())
                .serialNumber(dto.serialNumber())
                .dateOfPurchasing(dto.dateOfPurchasing())
                .type(dto.toolType())
                .place(place)
                .vendor(vendor)
                .description(dto.description())
                .build();
    }

    public static ToolResponse entityToResponse(Tool entity) {
        if (entity == null) return null;
        return ToolResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .toolType(entity.getType())
                .dateOfPurchasing(entity.getDateOfPurchasing())
                .serialNumber(entity.getSerialNumber())
                .vendor(VendorMapper.entityToDto(entity.getVendor()))
                .place(PlaceMapper.entityToResponse(entity.getPlace()))
                .build();
    }

    public static List<ToolResponse> entitiesToResponses(List<Tool> entityList) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(e -> ToolMapper.entityToResponse(e))
                .collect(Collectors.toList());
    }
}
