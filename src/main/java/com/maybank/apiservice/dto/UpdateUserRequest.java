package com.maybank.apiservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @NotNull
    private Long id;
    
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;
}
