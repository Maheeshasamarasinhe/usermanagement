package com.example.user_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bank_accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank_account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNumber;

    private String accountHolderName;
    private String accountType;
    private String bankName;
    private String branchName;
    @Column(nullable = false)
    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
     protected void prePersist() {
        this.status = true;
    }
}
