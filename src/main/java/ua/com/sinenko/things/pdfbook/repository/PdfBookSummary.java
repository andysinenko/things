package ua.com.sinenko.things.pdfbook.repository;

import ua.com.sinenko.things.pdfbook.dto.CategoryDto;
import ua.com.sinenko.things.pdfbook.dto.PdfAuthorDto;
import ua.com.sinenko.things.pdfbook.entity.Category;
import ua.com.sinenko.things.pdfbook.entity.PdfAuthor;

import java.time.LocalDate;
import java.util.List;

public interface PdfBookSummary {
    String getId();
    String getTitle();
    Category getCategory();
    List<PdfAuthor> getAuthors();
    String getYearOfRelease();
    LocalDate getUploadDate();
}
