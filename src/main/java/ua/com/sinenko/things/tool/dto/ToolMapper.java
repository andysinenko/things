package ua.com.sinenko.things.tool.dto;

import ua.com.sinenko.things.tool.entity.Tool;
import ua.com.sinenko.things.tool.entity.ToolType;
import ua.com.sinenko.things.tool.entity.Vendor;

import java.util.List;
import java.util.stream.Collectors;

public class ToolMapper {
    public static Tool mapDtoToEntity(ToolDto dto) {
        Vendor vendor = new Vendor();
        vendor.setId(dto.vendorDto().id());
        vendor.setName(dto.vendorDto().name());
        return Tool.builder()
                .id(dto.id())
                .name(dto.name())
                .description(dto.description())
                .type(ToolType.valueOf(dto.type()))
                .dateOfPurchasing(dto.dateOfPurchasing())
                .vendor(vendor)
                .serialNumber(dto.serialNumber())
                .build();
    }

    public static ToolDto mapEntityToDto(Tool entity) {
        VendorDto vendor = new VendorDto(entity.getId(), entity.getName());
        return ToolDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType().name())
                .dateOfPurchasing(entity.getDateOfPurchasing())
                .vendorDto(vendor)
                .serialNumber(entity.getSerialNumber())
                .build();
    }

    public static List<ToolDto> mapEntitiesToDtos(List<Tool> entity) {
        return entity.stream()
                .map(e -> ToolMapper.mapEntityToDto(e))
                .collect(Collectors.toList());
    }
}
