package com.banking.bank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.bank_api.entity.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    // JpaRepository automatically provides save(), findAll(), findById(), and
    // deleteById()
}