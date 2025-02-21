package ua.com.sinenko.things.tool.dto;

import lombok.Builder;

import java.time.LocalDate;


@Builder
public record ToolDto(
        Long id,
        String type,
        VendorDto vendorDto,
        LocalDate dateOfPurchasing,
        String name,
        String serialNumber,
        String description) {
}
