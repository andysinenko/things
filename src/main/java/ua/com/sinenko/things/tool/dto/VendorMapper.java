package ua.com.sinenko.things.tool.dto;

import ua.com.sinenko.things.tool.entity.Vendor;

import java.util.List;

public class VendorMapper {
    public static VendorDto toVendorDto(Vendor vendor) {
        if (vendor == null) {
            return null;
        }
        return new VendorDto(vendor.getId(), vendor.getName());
    }

    public static Vendor toVendorEntity(VendorDto vendorDto) {
        if (vendorDto == null) {
            return null;
        }
        Vendor vendor = new Vendor();
        vendor.setId(vendorDto.id());
        vendor.setName(vendorDto.name());
        return vendor;
    }

    public static List<VendorDto> toVendorDtoList(List<Vendor> vendors) {
        if (vendors == null || vendors.isEmpty()) {
            return List.of();
        }
        return vendors.stream()
                .map(VendorMapper::toVendorDto)
                .toList();
    }
}
