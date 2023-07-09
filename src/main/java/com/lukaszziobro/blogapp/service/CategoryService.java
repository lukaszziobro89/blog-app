package com.lukaszziobro.blogapp.service;

import com.lukaszziobro.blogapp.entity.Category;
import com.lukaszziobro.blogapp.payload.CategoryDto;
import com.lukaszziobro.blogapp.repository.CategoryRepository;
import com.lukaszziobro.blogapp.utils.CategoryMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.mapToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        List<CategoryDto> categories = categoryRepository.findAll(pageable).stream()
                .map(c -> categoryMapper.mapToCategoryDto(c)).toList();
                return new PageImpl<>(categories);
    }
}
