package com.lukaszziobro.blogapp.controller;

import com.lukaszziobro.blogapp.payload.CategoryDto;
import com.lukaszziobro.blogapp.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Category")
@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        logger.info("adding category");
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") long id) {
        logger.info("retrieving Category with id: " + id);
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @GetMapping
    public Page<CategoryDto> getCategories(
            @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC, sort = "id")Pageable pageable){
        logger.info("retrieving all categories");
        return categoryService.getAllCategories(pageable);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto,
                                              @PathVariable("id") long id){
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto, id), HttpStatus.NO_CONTENT);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>("Category deleted", HttpStatus.OK);
    }

}
