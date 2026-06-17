package com.example.user_management_system.config;

import com.example.user_management_system.entity.Bank_account;
import com.example.user_management_system.entity.Group;
import com.example.user_management_system.entity.Role;
import com.example.user_management_system.entity.User;
import com.example.user_management_system.repository.BankAccountRepository;
import com.example.user_management_system.repository.GroupRepository;
import com.example.user_management_system.repository.RoleRepository;
import com.example.user_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    private final BankAccountRepository bankAccountRepository;

    @Bean
    CommandLineRunner seedData() {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            Role adminRole = new Role();
            adminRole.setName("ADMIN");

            Role managerRole = new Role();
            managerRole.setName("MANAGER");

            Role userRole = new Role();
            userRole.setName("USER");

            roleRepository.save(adminRole);
            roleRepository.save(managerRole);
            roleRepository.save(userRole);

            User admin = new User();
            admin.setUsername("john_admin");
            admin.setEmail("john.admin@example.com");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            admin.setTelephone("9876543210");
            admin.setStatus(true);

            User manager = new User();
            manager.setUsername("sara_manager");
            manager.setEmail("sara.manager@example.com");
            manager.setPassword("manager123");
            manager.setRole("MANAGER");
            manager.setTelephone("9876543211");
            manager.setStatus(true);

            User regularUser = new User();
            regularUser.setUsername("mike_user");
            regularUser.setEmail("mike.user@example.com");
            regularUser.setPassword("user123");
            regularUser.setRole("USER");
            regularUser.setTelephone("9876543212");
            regularUser.setStatus(true);

            userRepository.save(admin);
            userRepository.save(manager);
            userRepository.save(regularUser);

            adminRole.getUsers().add(admin);
            managerRole.getUsers().add(manager);
            userRole.getUsers().add(regularUser);
            roleRepository.save(adminRole);
            roleRepository.save(managerRole);
            roleRepository.save(userRole);

            Group adminGroup = new Group();
            adminGroup.setName("Admin Group");
            adminGroup.setDescription("Group for admin users");

            Group managerGroup = new Group();
            managerGroup.setName("Manager Group");
            managerGroup.setDescription("Group for manager users");

            Group userGroup = new Group();
            userGroup.setName("User Group");
            userGroup.setDescription("Group for regular users");

            adminGroup.getUsers().add(admin);
            managerGroup.getUsers().add(regularUser);
            userGroup.getUsers().add(manager);

            groupRepository.save(adminGroup);
            groupRepository.save(managerGroup);
            groupRepository.save(userGroup);

            Bank_account adminAccount = new Bank_account();
            adminAccount.setAccountNumber("1234567890");
            adminAccount.setAccountHolderName("John Admin");
            adminAccount.setAccountType("Savings");
            adminAccount.setBankName("Bank A");
            adminAccount.setBranchName("Branch X");
            adminAccount.setStatus(true);
            adminAccount.setUser(admin);

            Bank_account managerAccount = new Bank_account();
            managerAccount.setAccountNumber("2345678901");
            managerAccount.setAccountHolderName("Sara Manager");
            managerAccount.setAccountType("Checking");
            managerAccount.setBankName("Bank B");
            managerAccount.setBranchName("Branch Y");
            managerAccount.setStatus(true);
            managerAccount.setUser(manager);

            Bank_account userAccount = new Bank_account();
            userAccount.setAccountNumber("3456789012");
            userAccount.setAccountHolderName("Mike User");
            userAccount.setAccountType("Savings");
            userAccount.setBankName("Bank C");
            userAccount.setBranchName("Branch Z");
            userAccount.setStatus(true);
            userAccount.setUser(regularUser);

            bankAccountRepository.save(adminAccount);
            bankAccountRepository.save(managerAccount);
            bankAccountRepository.save(userAccount);
        };
    }
}