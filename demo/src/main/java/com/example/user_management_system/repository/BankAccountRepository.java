package com.example.user_management_system.repository;

import com.example.user_management_system.entity.Bank_account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<Bank_account, Long> {

    boolean existsByAccountNumber(String accountNumber);
    List<Bank_account> findByUserId(Long userId);
}
