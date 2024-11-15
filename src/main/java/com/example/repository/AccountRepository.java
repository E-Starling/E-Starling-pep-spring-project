package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String>{
    //Check if username exists
    boolean existsByUsername(String username);
    //Check if id exists
    boolean existsByAccountId(Integer accountId);
    //Login account
    Optional<Account> findByUsernameAndPassword(String username, String password);

    
}
