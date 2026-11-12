package com.codingShuttle.Ashish.Caching_Concurrency.repository;

import com.codingShuttle.Ashish.Caching_Concurrency.entity.SalaryAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SalaryAccountRepository extends CrudRepository<SalaryAccount, Long> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SalaryAccount> findById(Long id);
}

// Here we could import from JpaRepository as well. JpaRepository actually extends CrudRepository and adds more functionality such as pagination, sorting, and JPA-specific methods. It's just that we need some basic CRUD operations only that's why we imported CRUDRepository only.
/*
                Optimistic locking
                    @Version
                        ↓
            "Let conflicts happen, but detect them." It allow the conflict to happen but will not let the conflicting code to execute and make update in DB.

                Pessimistic locking // means we lock before using.
                @Lock(PESSIMISTIC_WRITE)
                        ↓
            "Prevent conflicting updates by locking the row."
 */

// If we are using Pessimistic locking, then we don't need to use version with it. But in our code we are using version for extra safety so that what if someone else try to update it.
