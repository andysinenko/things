package ua.com.sinenko.things.tool.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record ToolDto(
        Long id,
        String type,
        String vendor,
        Date dateOfPurchasing,
        String name,
        String serialNumber,
        String description) {
}
