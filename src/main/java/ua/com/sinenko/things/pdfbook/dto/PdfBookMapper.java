package ua.com.sinenko.things.pdfbook.dto;

import org.springframework.data.domain.Page;
import ua.com.sinenko.things.pdfbook.schema.PdfBook;

import java.time.format.DateTimeFormatter;

public class PdfBookMapper {
    private final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

    public static PdfBookPageResponse entityToResponse(Page<PdfBook> pageEntity) {
            return PdfBookPageResponse.builder()
                .pdfbooks(pageEntity.getContent().stream().map(pdfBook -> {
                    return PdfBookResponse
                            .builder()
                            .id(pdfBook.getId())
                            .title(pdfBook.getTitle())
                            .yearOfRelease(pdfBook.getYearOfRelease()!=null?pdfBook.getYearOfRelease().toString():null)
                            .authors(pdfBook.getAuthors()
                                    .stream()
                                    .map(e -> PdfAuthorDto
                                            .builder()
                                            .id(e.getId())
                                            .name(e.getName())
                                            .build())
                                    .toList())
                            .language(pdfBook.getLanguage())
                            .build();
                        }).toList())
                .pageNumber(pageEntity.getNumber())
                .pageSize(pageEntity.getSize())
                .total(pageEntity.getTotalPages())
                .build();
    }


}
