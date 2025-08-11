package ua.com.sinenko.things.tool.dto;

import ua.com.sinenko.things.place.dto.PlaceMapper;
import ua.com.sinenko.things.tool.entity.Tool;
import ua.com.sinenko.things.tool.entity.Vendor;

import java.util.List;
import java.util.stream.Collectors;

public class ToolMapper {
    public static Tool mapDtoToEntity(ToolDto dto) {
        Vendor vendor = new Vendor();
        vendor.setId(dto.vendor().id());
        vendor.setName(dto.vendor().name());
        return Tool.builder()
                .id(dto.id())
                .name(dto.name())
                .serialNumber(dto.serialNumber())
                .dateOfPurchasing(dto.dateOfPurchasing())
                .type(dto.toolType())
                .vendor(vendor)
                .place(PlaceMapper.mapDtoToEntity(dto.place()))
                .description(dto.description())
                .build();
    }

    public static ToolDto mapEntityToDto(Tool entity) {
        VendorDto vendor = new VendorDto(entity.getId(), entity.getName());
        return ToolDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .toolType(entity.getType())
                .place(PlaceMapper.mapEntityToDto(entity.getPlace()))
                .dateOfPurchasing(entity.getDateOfPurchasing())
                .vendor(vendor)
                .serialNumber(entity.getSerialNumber())
                .build();
    }

    public static List<ToolDto> mapEntitiesToDtos(List<Tool> entity) {
        return entity.stream()
                .map(e -> ToolMapper.mapEntityToDto(e))
                .collect(Collectors.toList());
    }
}
