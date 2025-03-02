package com.bankApp.bankApp.service;

import com.bankApp.bankApp.entity.Account;
import com.bankApp.bankApp.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransferService {

    private Map<String, Account> accounts = new HashMap<>(); // In-memory storage for accounts


    public boolean transfer(Transaction transaction) {
        Account sourceAccount = accounts.get(transaction.getSourceAccountNumber());
        Account destinationAccount = accounts.get(transaction.getDestinationAccountNumber());

        if (sourceAccount == null || destinationAccount == null) {
            return false; // One or both accounts do not exist
        }

        if (sourceAccount.getBalance() < transaction.getAmount()) {
            return false; // Insufficient balance in the source account
        }

        // Perform the transfer
        sourceAccount.setBalance(sourceAccount.getBalance() - transaction.getAmount());
        destinationAccount.setBalance(destinationAccount.getBalance() + transaction.getAmount());
        return true;
    }
}
