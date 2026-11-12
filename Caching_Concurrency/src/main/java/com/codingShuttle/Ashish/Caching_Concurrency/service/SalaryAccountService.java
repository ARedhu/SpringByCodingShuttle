package com.codingShuttle.Ashish.Caching_Concurrency.service;


import com.codingShuttle.Ashish.Caching_Concurrency.entity.Employee;
import com.codingShuttle.Ashish.Caching_Concurrency.entity.SalaryAccount;

public interface SalaryAccountService {
    void createAccount(Employee employee);

    SalaryAccount incrementBalance(Long accountId);
}
