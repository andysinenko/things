package com.synenko.things.tool.dto;

import com.synenko.things.book.dto.BookPageResponse;
import com.synenko.things.book.dto.BookResponse;
import com.synenko.things.place.dto.PlaceMapper;
import com.synenko.things.place.entity.Place;
import com.synenko.things.tool.entity.Tool;
import com.synenko.things.tool.entity.Vendor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ToolMapper {
    public static Tool dtoToEntity(ToolRequest dto, Place place, Vendor vendor) {
        if (dto == null) return null;
        return Tool.builder()
                .name(dto.name())
                .serialNumber(dto.serialNumber())
                .dateOfPurchasing(dto.dateOfPurchasing())
                .type(dto.toolType())
                .place(place)
                .vendor(vendor)
                .description(dto.description())
                .build();
    }

    public static ToolResponse entityToResponse(Tool entity) {
        if (entity == null) return null;
        return ToolResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .toolType(entity.getType())
                .dateOfPurchasing(entity.getDateOfPurchasing())
                .serialNumber(entity.getSerialNumber())
                .vendor(VendorMapper.entityToDto(entity.getVendor()))
                .place(PlaceMapper.entityToResponse(entity.getPlace()))
                .build();
    }

    public static ToolPageResponse entityToPagebleResponse(Page<Tool> pageEntity) {
        return ToolPageResponse.builder()
                .tools(pageEntity.get().map(e -> ToolMapper.entityToResponse(e)).collect(Collectors.toList()))
                .pageNumber(pageEntity.getNumber())
                .pageSize(pageEntity.getSize())
                .total(pageEntity.getTotalPages())
                .build();
    }

    public static List<ToolResponse> pagableToResponses(Page<Tool> entityList) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(e -> ToolMapper.entityToResponse(e))
                .collect(Collectors.toList());
    }

    public static List<ToolResponse> entitiesToResponses(List<Tool> entityList) {
        if (entityList == null) return null;
        return entityList.stream()
                .map(e -> ToolMapper.entityToResponse(e))
                .collect(Collectors.toList());
    }
}
