package com.bankApp.bankApp.service.impl;

import com.bankApp.bankApp.dto.AccountDto;
import com.bankApp.bankApp.entity.Account;
import com.bankApp.bankApp.mapper.AccountMapper;
import com.bankApp.bankApp.repository.AccountRepository;
import com.bankApp.bankApp.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    // Constructor injection
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create a new account
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    // Get account details by ID
    @Override
    public AccountDto getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(AccountMapper::mapToAccountDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    // Deposit amount to an account
    @Override
    public AccountDto deposit(Long id, double amount) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() ->new RuntimeException("Account does not exists"));

        // Update balance after deposit
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount =  accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    // Withdraw
    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND,"Account does not exists"));

        // Check if the account has sufficient balance
        if(account.getBalance() < amount){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient amount");
        }

        // Update balance after withdrawal
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

        // If balance reaches zero, return a specific message
        if (total == 0) {
            throw new ResponseStatusException(HttpStatus.OK, "Your account balance is now zero!");
        }
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    // Get all accounts
    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }

    // Delete the account
    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() ->new RuntimeException("Account does not exists"));
        accountRepository.deleteById(id);
    }

    // Get account details by account number
    @Override
    public AccountDto getAccountByAccountNumber(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(AccountMapper::mapToAccountDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }

    // Transfer amount from one account to another
    @Override
    public void transfer(Long sourceAccountNumber, Long destinationAccountNumber, double amount) {
        Account sourceAccount = accountRepository
                .findByAccountNumber(sourceAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source Account does not exist"));

        Account destinationAccount = accountRepository
                .findByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination Account does not exist"));

        if (sourceAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in source account");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        accountRepository.save(sourceAccount);

        destinationAccount.setBalance(destinationAccount.getBalance() + amount);
        accountRepository.save(destinationAccount);
    }
}
