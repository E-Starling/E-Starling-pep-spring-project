package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Register Account
    public Account registerAccount(Account account) {
        // Empty username
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannont be null or empty");
        }
        // Pass less than 4 char
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }
        // Duplicate username
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        return accountRepository.save(account);
    }

    // Login Account
    public Account loginAccount(Account account) {
        // Empty username
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannont be null or empty");
        }
        // Empty password
        if (account.getPassword() == null || account.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        // Attempt to login
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("Invalid login"));
    }
}
