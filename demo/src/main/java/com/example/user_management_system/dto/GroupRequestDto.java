package com.example.user_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupRequestDto {

    @NotBlank(message = "Group name is required")
    private String name;
    
    private String description;
}
