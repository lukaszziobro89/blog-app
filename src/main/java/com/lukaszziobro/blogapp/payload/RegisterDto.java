package com.lukaszziobro.blogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Email cannot be empty")
    @Email
    private String email;

    @NotNull(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Role cannot be empty")
    private String role;
}
