package com.codingShuttle.Ashish.Caching_Concurrency.service.impl;

import com.codingShuttle.Ashish.Caching_Concurrency.entity.Employee;
import com.codingShuttle.Ashish.Caching_Concurrency.entity.SalaryAccount;
import com.codingShuttle.Ashish.Caching_Concurrency.repository.SalaryAccountRepository;
import com.codingShuttle.Ashish.Caching_Concurrency.service.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class SalaryAccountServiceImpl implements SalaryAccountService {

    private final SalaryAccountRepository salaryAccountRepository;

    @Override
    public void createAccount(Employee employee) {

//        if(employee.getName().equals("Ashish")) throw new RuntimeException("Ashish is not allowed");

        SalaryAccount salaryAccount = SalaryAccount.builder()
                .employee(employee)
                .balance(BigDecimal.ZERO)
                .build();

        salaryAccountRepository.save(salaryAccount);
    }

    @Override
  //  @Transactional(isolation = Isolation.SERIALIZABLE) // Even this not guarantee to resolve the lost update problem(if 100 requests comes concurrently to update the balance, how we will handle it).
    // It can handled only via pesimistic locking in the repository.
    public SalaryAccount incrementBalance(Long accountId) {

        SalaryAccount salaryAccount = salaryAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal prevBalance = salaryAccount.getBalance();
        BigDecimal newBalance = prevBalance.add(BigDecimal.valueOf(1L));

        salaryAccount.setBalance(newBalance);

        return salaryAccountRepository.save(salaryAccount);
    }
}

