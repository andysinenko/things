package ua.com.sinenko.things.pdfbook.dto;

import ua.com.sinenko.things.pdfbook.entity.PdfAuthor;
import java.util.List;

public class PdfAuthorMapper {
    public static PdfAuthor toEntity(PdfAuthorDto dto) {
        return PdfAuthor.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    public static PdfAuthorDto toDto(PdfAuthor entity) {
        return PdfAuthorDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static List<PdfAuthor> toEntityList(List<PdfAuthorDto> dtoList) {
        return dtoList.stream().map(e -> PdfAuthorMapper.toEntity(e)).toList();
    }

    public static List<PdfAuthorDto> toDtoList(List<PdfAuthor> entityList) {
        return entityList.stream().map(e -> PdfAuthorMapper.toDto(e)).toList();
    }
}
