package ua.com.sinenko.things.tool.dto;

import ua.com.sinenko.things.tool.entity.Vendor;
import java.util.List;

public class VendorMapper {
    public static VendorDto entityToDto(Vendor entity) {
        if (entity == null) {
            return null;
        }
        return new VendorDto(entity.getId(), entity.getName());
    }

    public static Vendor dtoToEntity(VendorDto dto) {
        if (dto == null) return null;
        return Vendor.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    public static List<VendorDto> entitiesToDtos(List<Vendor> vendors) {
        if (vendors == null || vendors.isEmpty()) return List.of();
        return vendors.stream()
                .map(VendorMapper::entityToDto)
                .toList();
    }
}
