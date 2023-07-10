package com.lukaszziobro.blogapp.service;

import com.lukaszziobro.blogapp.entity.Category;
import com.lukaszziobro.blogapp.exception.ResourceNotFoundException;
import com.lukaszziobro.blogapp.payload.CategoryDto;
import com.lukaszziobro.blogapp.repository.CategoryRepository;
import com.lukaszziobro.blogapp.utils.CategoryMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

    private final static Logger logger = LoggerFactory.getLogger(CategoryService.class);

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.mapToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    public CategoryDto getCategoryById(long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return categoryMapper.mapToCategoryDto(category);
    }

    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        List<CategoryDto> categories = categoryRepository.findAll(pageable).stream()
                .map(c -> categoryMapper.mapToCategoryDto(c)).toList();
                return new PageImpl<>(categories);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        Category categoryToBeSaved = categoryMapper.mapToCategoryById(categoryDto, category);
        Category categorySaved = categoryRepository.save(categoryToBeSaved);
        return categoryMapper.mapToCategoryDto(categorySaved);
    }

    public void deleteCategoryById(long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }

}
