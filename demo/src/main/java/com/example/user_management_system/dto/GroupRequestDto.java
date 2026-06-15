package com.example.user_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequestDto {

    @NotBlank(message = "Group name is required")
    private String name;

    @NotBlank(message = "Group description is required")
    private String description;
}
