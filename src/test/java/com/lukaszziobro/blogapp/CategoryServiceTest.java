package com.lukaszziobro.blogapp;

import com.lukaszziobro.blogapp.payload.CategoryDto;
import com.lukaszziobro.blogapp.service.CategoryService;
import com.lukaszziobro.blogapp.utils.CategoryMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    @DisplayName("Test create new category")
    @Order(1)
    public void testCreateCategory() {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("category name 4")
                .description("category description 4")
                .build();

        CategoryDto categorySaved = categoryService.addCategory(categoryDto);

        Assertions.assertThat(categorySaved).isNotNull();
        Assertions.assertThat(categorySaved.getId()).isEqualTo(4);
        Assertions.assertThat(categorySaved.getName()).isEqualTo("category name 4");
        Assertions.assertThat(categorySaved.getDescription()).isEqualTo("category description 4");

    }

    @Test
    @DisplayName("Test get all categories")
    @Order(2)
    public void testGetAllCategories() {
        Pageable wholePage = Pageable.unpaged();
        List<CategoryDto> categories = categoryService.getAllCategories(wholePage).stream().toList();
        Assertions.assertThat(categories.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Test get category by category id")
    @Order(3)
    public void testGetCategoryByCategoryId() {
        CategoryDto categoryDto = categoryService.getCategoryById(3);
        Assertions.assertThat(categoryDto.getId()).isEqualTo(3);
        Assertions.assertThat(categoryDto.getName()).isEqualTo("category name 3");
        Assertions.assertThat(categoryDto.getDescription()).isEqualTo("category description 3");
    }

    @Test
    @DisplayName("Test update category")
    @Order(4)
    public void testUpdateCategory() {//TODO

    }

    @Test
    @DisplayName("Test delete category")
    @Order(5)
    public void testDeleteCategory() {//TODO

    }

    @Test
    @DisplayName("Test get category by id throws resource not found exception")
    @Order(4)
    public void testGetCategoryByIdThrowsResourceNotFoundException(){//TODO

    }

}
