package com.synenko.things.pdfbook.dto;

import com.synenko.things.book.dto.AuthorResponse;
import com.synenko.things.pdfbook.entity.PdfAuthor;

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