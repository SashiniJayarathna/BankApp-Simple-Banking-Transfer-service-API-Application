package com.bankApp.bankApp.controller;

import com.bankApp.bankApp.dto.AccountDto;
import com.bankApp.bankApp.dto.TransferRequest;
import com.bankApp.bankApp.service.TransferService;
import com.bankApp.bankApp.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final TransferService transferService;

    // Constructor injection
    public AccountController(AccountService accountService, TransferService transferService){
        this.accountService = accountService;
        this.transferService = transferService;
    }

    // Get Account by ID
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        try {
            AccountDto accountDto = accountService.getAccountById(id);
            return ResponseEntity.ok(accountDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Avoid exposing sensitive error details
        }
    }

    // Add new account    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    // Get Account by Account Number
    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<AccountDto> getAccountByAccountNumber(@PathVariable Long accountNumber){
        AccountDto accountDto = accountService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(accountDto);
    }

    // Deposit
    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long accountNumber, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accountDto =  accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok(accountDto);
    }

    // Withdraw
    @PutMapping("/{accountNumber}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long accountNumber, @RequestBody Map<String, Double> request) {
        double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(accountNumber, amount);
        return ResponseEntity.ok(accountDto);
    }

    // Get all accounts
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // Delete account
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok("Account is deleted successfully!");
    }

    // Transfer between accounts
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest transferRequest) {
        accountService.transfer(
                transferRequest.getSourceAccountNumber(),
                transferRequest.getDestinationAccountNumber(),
                transferRequest.getAmount()
        );
        return ResponseEntity.ok("Transfer successful!");
    }
}
