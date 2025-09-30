package ua.com.sinenko.things.pdfbook.dto;

import ua.com.sinenko.things.pdfbook.entity.Category;
import ua.com.sinenko.things.pdfbook.schema.CategorySchema;

import java.util.List;

public class CategoryMapper {
    public static Category toEntity(CategoryDto dto) {
        return Category.builder()
                .id(dto.id())
                .name(dto.name())
                .build();
    }

    public static CategoryDto toDto(Category entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static CategoryDto toDtoFromSchema(CategorySchema schema) {
        if (schema != null) {
            return CategoryDto.builder()
                    .id(schema.getId())
                    .name(schema.getName())
                    .build();
        }
        return null;
    }

    public static CategorySchema toSchema(Category entity) {
        return CategorySchema.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static List<Category> toEntityList(List<CategoryDto> dtoList) {
        return dtoList.stream().map(e -> CategoryMapper.toEntity(e)).toList();
    }

    public static List<CategoryDto> toDtoList(List<Category> entityList) {
        return entityList.stream().map(e -> CategoryMapper.toDto(e)).toList();
    }
}
