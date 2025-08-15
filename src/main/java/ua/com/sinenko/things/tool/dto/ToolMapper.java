package ua.com.sinenko.things.tool.dto;

import ua.com.sinenko.things.place.dto.PlaceMapper;
import ua.com.sinenko.things.tool.entity.Tool;

import java.util.List;
import java.util.stream.Collectors;

public class ToolMapper {
    public static Tool mapDtoToEntity(ToolDto dto) {
        return Tool.builder()
                .id(dto.id())
                .name(dto.name())
                .serialNumber(dto.serialNumber())
                .dateOfPurchasing(dto.dateOfPurchasing())
                .type(dto.toolType())
                .description(dto.description())
                .build();
    }

    public static ToolResponse mapEntityToDto(Tool entity) {
        return ToolResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .toolType(entity.getType())
                .dateOfPurchasing(entity.getDateOfPurchasing())
                .serialNumber(entity.getSerialNumber())
                .vendor(VendorMapper.toVendorDto(entity.getVendor()))
                .place(PlaceMapper.mapEntityToDto(entity.getPlace()))
                .build();
    }

    public static List<ToolResponse> mapEntitiesToDtos(List<Tool> entity) {
        return entity.stream()
                .map(e -> ToolMapper.mapEntityToDto(e))
                .collect(Collectors.toList());
    }
}
