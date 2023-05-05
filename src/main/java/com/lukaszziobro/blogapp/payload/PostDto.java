package com.lukaszziobro.blogapp.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostDto {
    @NotNull(message = "Title cannot be empty")
    private String title;
    @NotNull(message = "Description cannot be empty")
    private String description;
    @NotNull(message = "Content cannot be empty")
    private String content;
}
