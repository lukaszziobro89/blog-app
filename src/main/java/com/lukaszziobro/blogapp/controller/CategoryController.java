package com.lukaszziobro.blogapp.controller;

import com.lukaszziobro.blogapp.payload.CategoryDto;
import com.lukaszziobro.blogapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);

    private CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        logger.info("adding category");
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<CategoryDto> getCategories(
            @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC, sort = "id")Pageable pageable){
        logger.info("retrieving all categories");
        return categoryService.getAllCategories(pageable);
    }

}
