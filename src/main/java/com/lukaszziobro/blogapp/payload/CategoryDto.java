package com.lukaszziobro.blogapp.payload;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private Long id;

    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Description cannot be empty")
    private String description;
}
