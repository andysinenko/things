package com.synenko.things.tool.dto;

import com.synenko.things.tool.entity.Vendor;
import java.util.List;

public class VendorMapper {
    public static VendorResponse entityToDto(Vendor entity) {
        if (entity == null) {
            return null;
        }
        return new VendorResponse(entity.getId(), entity.getName());
    }

    public static Vendor dtoToEntity(VendorRequest dto) {
        if (dto == null) return null;
        return Vendor.builder()
                .name(dto.name())
                .build();
    }

    public static List<VendorResponse> entitiesToDtos(List<Vendor> vendors) {
        if (vendors == null || vendors.isEmpty()) return List.of();
        return vendors.stream()
                .map(VendorMapper::entityToDto)
                .toList();
    }
}
