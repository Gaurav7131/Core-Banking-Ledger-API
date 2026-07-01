package com.banking.bank_api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.banking.bank_api.Entity.BankAccount;
import com.banking.bank_api.Repository.BankAccountRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountRepository repository;

    // 1. Create a new Bank Account
    @PostMapping
    public BankAccount createAccount(@RequestBody @NonNull BankAccount account) {
        return repository.save(account);
    }

    // 2. Get all Bank Accounts
    @GetMapping
    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    // 3. Get an account by ID
    @GetMapping("/{id}")
    public BankAccount getAccountById(@PathVariable @NonNull Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    // 4. Update an account (Deposit/Withdrawal)
    @PutMapping("/{id}")
    public BankAccount updateBalance(@NonNull Long id, @RequestParam double balance) {
        BankAccount account = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(balance);
        return repository.save(account);
    }

    // 5. Close/Delete an account
    @DeleteMapping("/{id}")
    public String deleteAccount(@NonNull Long id) {
        repository.deleteById(id);
        return "Account closed successfully.";
    }

    // 6. Transfer Money Between Accounts
    @Transactional
    @PostMapping("/transfer")
    public String transferMoney(
            @RequestParam @NonNull Long fromAccountId,
            @RequestParam @NonNull Long toAccountId,
            @RequestParam double amount) {

        // 1. Ensure the transfer amount is valid
        if (amount <= 0) {
            throw new RuntimeException("Transfer amount must be greater than zero.");
        }

        // 2. Find both accounts in the database
        BankAccount fromAccount = repository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Sender account not found."));

        BankAccount toAccount = repository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Receiver account not found."));

        // 3. Check for sufficient funds
        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds for transfer.");
        }

        // 4. Perform the transfer
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        // 5. Save the updated balances
        repository.save(fromAccount);
        repository.save(toAccount);

        return "Successfully transferred $" + amount + " from Account " + fromAccountId + " to Account " + toAccountId;
    }
}