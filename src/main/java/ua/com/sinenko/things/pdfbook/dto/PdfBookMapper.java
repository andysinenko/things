package ua.com.sinenko.things.pdfbook.dto;

import org.springframework.data.domain.Page;
import ua.com.sinenko.things.pdfbook.schema.PdfBookSchema;

import java.time.format.DateTimeFormatter;

public class PdfBookMapper {
    private final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

    public static PdfBookPageResponse entityToResponse(Page<PdfBookSchema> pageEntity) {
            return PdfBookPageResponse.builder()
                .pdfbooks(pageEntity.getContent().stream().map(pdfBookSchema -> {
                    return PdfBookResponse
                            .builder()
                            .id(pdfBookSchema.getId())
                            .title(pdfBookSchema.getTitle())
                            .yearOfRelease(pdfBookSchema.getYearOfRelease())
                            .category(CategoryMapper.toDtoFromSchema(pdfBookSchema.getCategory()))
                            .author(PdfAuthorMapper.toDtoFromSchema(pdfBookSchema.getAuthor()))
                            .language(pdfBookSchema.getLanguage())
                            .numberOfPages(pdfBookSchema.getNumberOfPages())
                            .build();
                        }).toList())
                .pageNumber(pageEntity.getNumber())
                .pageSize(pageEntity.getSize())
                .total(pageEntity.getTotalPages())
                .build();
    }


    public static PdfBookResponse entityToResponseWOContent(PdfBookSchema entity) {
        return PdfBookResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .yearOfRelease(entity.getYearOfRelease())
                .language(entity.getLanguage())
                .category(CategoryMapper.toDtoFromSchema(entity.getCategory()))
                .numberOfPages(entity.getNumberOfPages())
                .author(PdfAuthorMapper.toDtoFromSchema(entity.getAuthor()))
                .build();
    }


}
