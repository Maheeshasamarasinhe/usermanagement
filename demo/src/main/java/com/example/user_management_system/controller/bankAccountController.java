package com.example.user_management_system.controller;

import com.example.user_management_system.dto.BankAccountRequestDto;
import com.example.user_management_system.dto.BankAccountResponseDto;
import com.example.user_management_system.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank-accounts")
@RequiredArgsConstructor
public class bankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<BankAccountResponseDto> createBankAccount(@Valid @RequestBody BankAccountRequestDto dto) {
        return new ResponseEntity<>(bankAccountService.createBankAccount(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BankAccountResponseDto>> getAllBankAccounts() {
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BankAccountResponseDto> getBankAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(bankAccountService.getBankAccountById(id));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<BankAccountResponseDto> updateBankAccount(@PathVariable Long id, @Valid @RequestBody BankAccountRequestDto dto) {
        return ResponseEntity.ok(bankAccountService.updateBankAccount(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }


}
