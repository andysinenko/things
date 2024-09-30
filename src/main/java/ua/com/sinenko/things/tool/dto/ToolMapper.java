package ua.com.sinenko.things.tool.dto;

import ua.com.sinenko.things.tool.entity.Tool;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ToolMapper {
    public static Tool mapDtoToEntity(ToolDto dto) {
        return Tool.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .type(dto.type())
                .dateOfPurchasing(dto.dateOfPurchasing())
                .vendor(dto.vendor())
                .serialNumber(dto.serialNumber())
                .build();
    }

    public static ToolDto mapEntityToDto(Tool entity) {
        return ToolDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType())
                .dateOfPurchasing(entity.getDateOfPurchasing())
                .vendor(entity.getVendor())
                .serialNumber(entity.getSerialNumber())
                .build();
    }

    public static List<ToolDto> mapEntitiesToDtos(List<Tool> entity) {
        return entity.stream()
                .map(e -> ToolMapper.mapEntityToDto(e))
                .collect(Collectors.toList());
    }
}
