package com.banking.bank_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import com.banking.bank_api.repository.BankAccountRepository;

import java.util.function.Function;

@Configuration
public class BankingAgentTools {

    private final BankAccountRepository repository;

    public BankingAgentTools(BankAccountRepository repository) {
        this.repository = repository;
    }

    // Structured Typed Records
    public record AccountRequest(Long accountId) {
    }

    public record TransferRequest(Long fromId, Long toId, double amount) {
    }

    // Dynamic Tool Calling
    @Bean
    @Description("Get the current account balance for a specific bank account ID.")
    public Function<AccountRequest, String> checkBalance() {
        return request -> repository.findById(request.accountId())
                .map(acc -> "Balance for account " + request.accountId() + " is $" + acc.getBalance())
                .orElse("Account ID " + request.accountId() + " not found.");
    }

    @Bean
    @Description("Transfer money between two bank accounts securely.")
    public Function<TransferRequest, String> transferMoneyTool() {
        return request -> {
            // In a real app, you would call your @Transactional Service method here
            return "Successfully initiated transfer of $" + request.amount() +
                    " from account " + request.fromId() + " to account " + request.toId();
        };
    }
}