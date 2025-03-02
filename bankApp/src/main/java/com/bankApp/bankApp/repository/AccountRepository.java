package com.bankApp.bankApp.repository;

import com.bankApp.bankApp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(Long accountNumber);
}
