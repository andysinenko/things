package ua.com.sinenko.things.pdfbook.dto;

import ua.com.sinenko.things.book.dto.AuthorResponse;
import ua.com.sinenko.things.pdfbook.entity.PdfAuthor;

import java.util.List;

public class PdfAuthorMapper {

    public static PdfAuthor toEntity(PdfAuthorRequest request) {
        return PdfAuthor.builder()
                .name(request.name())
                .build();
    }

    public static PdfAuthorResponse toDto(PdfAuthor entity) {
        return PdfAuthorResponse.builder()
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

    public static List<PdfAuthorResponse> toDtoList(List<PdfAuthor> entityList) {
        return entityList.stream().map(PdfAuthorMapper::toDto).toList();
    }
}