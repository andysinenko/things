package ua.com.sinenko.things.tool.dto;

import ua.com.sinenko.things.place.dto.PlaceMapper;
import ua.com.sinenko.things.tool.entity.Tool;

import java.util.List;
import java.util.stream.Collectors;

public class ToolMapper {
    public static Tool dtoToEntity(ToolDto dto) {
        if (dto == null) return null;
        return Tool.builder()
                .id(dto.id())
                .name(dto.name())
                .serialNumber(dto.serialNumber())
                .dateOfPurchasing(dto.dateOfPurchasing())
                .type(dto.toolType())
                .place(PlaceMapper.dtoToEntity(dto.place()))
                .vendor(VendorMapper.dtoToEntity(dto.vendor()))
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
                .place(PlaceMapper.entityToDto(entity.getPlace()))
                .build();
    }

    public static List<ToolResponse> entitiesToResponses(List<Tool> entityList) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(e -> ToolMapper.entityToResponse(e))
                .collect(Collectors.toList());
    }
}
