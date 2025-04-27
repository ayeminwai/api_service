package com.maybank.apiservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KycUserResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String website;
}
