package com.example.user_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BankAccountRequestDto {

    @NotNull(message = "Account number is required")

    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only digits")
    private String accountNumber;

//    @NotBlank(message = "Account holder name is required")
//    private String accountHolderName;

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "Branch name is required")
    private String branchName;

    @NotNull(message = "User ID is required")
    private Long userId;
}
