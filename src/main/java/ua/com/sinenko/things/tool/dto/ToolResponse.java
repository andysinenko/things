package ua.com.sinenko.things.tool.dto;

import lombok.Builder;

import ua.com.sinenko.things.place.dto.PlaceResponse;
import ua.com.sinenko.things.tool.entity.ToolType;

import java.time.LocalDate;


@Builder
public record ToolResponse(
        Long id,
        String name,
        ToolType toolType,
        String serialNumber,
        VendorRequest vendor,
        PlaceResponse place,
        LocalDate dateOfPurchasing,
        String description) {
}
