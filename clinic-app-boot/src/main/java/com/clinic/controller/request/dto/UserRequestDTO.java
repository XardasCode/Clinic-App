package com.clinic.controller.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {
    @NotBlank(message = "Name is mandatory field")
    private String username;

    @NotBlank(message = "Email is mandatory field")
    private String email;

    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;
}
