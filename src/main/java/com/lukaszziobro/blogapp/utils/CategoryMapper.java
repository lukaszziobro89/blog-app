package com.lukaszziobro.blogapp.utils;

import com.lukaszziobro.blogapp.entity.Category;
import com.lukaszziobro.blogapp.payload.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "posts", ignore = true)
    Category mapToCategory(CategoryDto categoryDto);

    CategoryDto mapToCategoryDto(Category category);

}
