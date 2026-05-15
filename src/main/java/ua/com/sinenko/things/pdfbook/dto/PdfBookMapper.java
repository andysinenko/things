package ua.com.sinenko.things.pdfbook.dto;

import org.springframework.data.domain.Page;
import ua.com.sinenko.things.pdfbook.entity.PdfBook;

public class PdfBookMapper {

    public static PdfBookPageResponse entityToResponse(Page<PdfBook> page) {
        return PdfBookPageResponse.builder()
                .pdfbooks(page.getContent().stream()
                        .map(PdfBookMapper::toResponse)
                        .toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .total(page.getTotalPages())
                .build();
    }

    public static PdfBookResponse toResponse(PdfBook entity) {
        return PdfBookResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .yearOfRelease(entity.getYearOfRelease())
                .language(entity.getLanguage())
                .category(entity.getCategory() != null
                        ? CategoryMapper.toDto(entity.getCategory()) : null)
                .author(entity.getAuthor() != null
                        ? PdfAuthorMapper.toDto(entity.getAuthor()) : null)
                .numberOfPages(entity.getNumberOfPages())
                .build();
    }
}