package ua.com.sinenko.things.pdfbook.dto;

import ua.com.sinenko.things.book.dto.AuthorDto;
import ua.com.sinenko.things.pdfbook.entity.PdfAuthor;
import ua.com.sinenko.things.pdfbook.schema.Author;

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


    public static PdfAuthorDto toDtoFromSchema(Author schema) {
        return PdfAuthorDto.builder()
                .id(schema.getId())
                .name(schema.getName())
                .build();
    }

    public static Author toSchemaFromEntity(PdfAuthor entity) {
        return Author.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static Author toSchemaFromDto(AuthorDto dto) {
        return Author.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }


    public static List<PdfAuthor> toEntityList(List<PdfAuthorDto> dtoList) {
        return dtoList.stream().map(e -> PdfAuthorMapper.toEntity(e)).toList();
    }

    public static List<PdfAuthorDto> toDtoList(List<PdfAuthor> entityList) {
        return entityList.stream().map(e -> PdfAuthorMapper.toDto(e)).toList();
    }

    public static List<PdfAuthorDto> toDtoListFromSchema(List<Author> authors) {
        return authors.stream().map(e -> PdfAuthorMapper.toDtoFromSchema(e)).toList();
    }
}
