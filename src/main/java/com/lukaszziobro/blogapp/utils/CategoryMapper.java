package com.lukaszziobro.blogapp.utils;

import com.lukaszziobro.blogapp.entity.Category;
import com.lukaszziobro.blogapp.payload.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "posts", ignore = true)
    Category mapToCategory(CategoryDto categoryDto);

    CategoryDto mapToCategoryDto(Category category);

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "id", ignore = true)
    Category mapToCategoryById(CategoryDto categoryDto, @MappingTarget Category category);

}
