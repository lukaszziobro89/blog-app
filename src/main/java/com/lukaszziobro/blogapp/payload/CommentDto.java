package com.lukaszziobro.blogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private Long id;
    @NotNull(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Not a valid email")
    private String email;
    @NotNull(message = "Body cannot be empty")
    private String body;

}
