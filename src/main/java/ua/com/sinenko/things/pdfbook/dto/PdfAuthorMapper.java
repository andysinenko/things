package ua.com.sinenko.things.pdfbook.dto;

import ua.com.sinenko.things.book.dto.AuthorResponse;
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

    // используется в save() когда автор пришёл из запроса, а не из PDF-метаданных
    public static PdfAuthor fromAuthorResponse(AuthorResponse dto) {
        return PdfAuthor.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    public static List<PdfAuthorDto> toDtoList(List<PdfAuthor> entityList) {
        return entityList.stream().map(PdfAuthorMapper::toDto).toList();
    }
}