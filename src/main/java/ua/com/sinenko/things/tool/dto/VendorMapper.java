package ua.com.sinenko.things.tool.dto;

import ua.com.sinenko.things.tool.entity.Vendor;
import java.util.List;

public class VendorMapper {
    public static VendorRequest entityToDto(Vendor entity) {
        if (entity == null) {
            return null;
        }
        return new VendorRequest(entity.getName());
    }

    public static Vendor dtoToEntity(VendorRequest dto) {
        if (dto == null) return null;
        return Vendor.builder()
                .name(dto.name())
                .build();
    }

    public static List<VendorRequest> entitiesToDtos(List<Vendor> vendors) {
        if (vendors == null || vendors.isEmpty()) return List.of();
        return vendors.stream()
                .map(VendorMapper::entityToDto)
                .toList();
    }
}
