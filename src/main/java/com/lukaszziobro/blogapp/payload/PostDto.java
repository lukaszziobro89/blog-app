package com.lukaszziobro.blogapp.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class PostDto {
    private Long id;
    @NotNull(message = "Title cannot be empty")
    private String title;
    @NotNull(message = "Description cannot be empty")
    private String description;
    @NotNull(message = "Content cannot be empty")
    private String content;
    private Set<CommentDto> comments;
}
