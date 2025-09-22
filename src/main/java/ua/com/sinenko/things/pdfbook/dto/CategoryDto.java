package ua.com.sinenko.things.pdfbook.dto;

import lombok.Builder;
import lombok.Data;

@Builder
public record CategoryDto(Long id, String name) { }
