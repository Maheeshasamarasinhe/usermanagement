package com.example.user_management_system.service.impl;

import com.example.user_management_system.dto.BankAccountRequestDto;
import com.example.user_management_system.dto.BankAccountResponseDto;
import com.example.user_management_system.entity.Bank_account;
import com.example.user_management_system.entity.User;
import com.example.user_management_system.exception.NotFoundException;
import com.example.user_management_system.repository.BankAccountRepository;
import com.example.user_management_system.repository.UserRepository;
import com.example.user_management_system.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tools.jackson.databind.util.BeanUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public BankAccountResponseDto createBankAccount(BankAccountRequestDto dto) {
        if (bankAccountRepository.existsByAccountNumber(dto.getAccountNumber())) {
            throw new NotFoundException("Bank account with number " + dto.getAccountNumber() + " already exists");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + dto.getUserId()));

        Bank_account bankAccount = new Bank_account();
        BeanUtils.copyProperties(dto, bankAccount);
        bankAccount.setUser(user);
        bankAccount.setAccountHolderName(user.getUsername());
        bankAccount.setStatus(true);
        return toResponseDto(bankAccountRepository.save(bankAccount));
    }

    @Override
    public List<BankAccountResponseDto> getAllBankAccounts() {
        return bankAccountRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountResponseDto getBankAccountById(Long id) {
        return toResponseDto(findById(id));
    }

    @Override
    public BankAccountResponseDto updateBankAccount(Long id, BankAccountRequestDto dto) {
        Bank_account existing = findById(id);
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + dto.getUserId()));

        existing.setAccountNumber(dto.getAccountNumber());
        existing.setAccountHolderName(user.getUsername());
        existing.setAccountType(dto.getAccountType());
        existing.setBankName(dto.getBankName());
        existing.setBranchName(dto.getBranchName());
        existing.setUser(user);
        return toResponseDto(bankAccountRepository.save(existing));
    }

    @Override
    public void deleteBankAccount(Long id) {
        Bank_account bankAccount = findById(id);
        bankAccount.setStatus(false);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<BankAccountResponseDto> getBankAccountsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found with id: " + userId);
        }
        return bankAccountRepository.findByUserId(userId).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private Bank_account findById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bank account not found with id: " + id));
    }

    private BankAccountResponseDto toResponseDto(Bank_account bankAccount) {
        BankAccountResponseDto dto = modelMapper.map(bankAccount, BankAccountResponseDto.class);
        if (bankAccount.getUser() != null) {
            dto.setUserId(bankAccount.getUser().getId());
            dto.setUsername(bankAccount.getUser().getUsername());
        }
        return dto;
    }
}
