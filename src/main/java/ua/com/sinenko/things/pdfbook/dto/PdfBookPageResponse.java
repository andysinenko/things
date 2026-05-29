package ua.com.sinenko.things.pdfbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "Response object for pdf books")
public record PdfBookPageResponse(
        @Schema(description = "List of pdf books according to pagination params")
        List<PdfBookResponse> pdfbooks,
        @Schema(description = "page number")
        int pageNumber,
        @Schema(description = "page size i.e. quamtity of bookss in response")
        int pageSize,
        @Schema(description = "total size of pages")
        int total
        ) {}
