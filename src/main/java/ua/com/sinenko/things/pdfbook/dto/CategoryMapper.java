package ua.com.sinenko.things.pdfbook.dto;

import ua.com.sinenko.things.pdfbook.entity.Category;
import java.util.List;

public class CategoryMapper {

    public static Category toEntity(CategoryRequest request) {
        return Category.builder()
                .name(request.name())
                .build();
    }

    public static CategoryResponse toDto(Category entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static List<CategoryResponse> toDtoList(List<Category> entityList) {
        return entityList.stream().map(CategoryMapper::toDto).toList();
    }
}