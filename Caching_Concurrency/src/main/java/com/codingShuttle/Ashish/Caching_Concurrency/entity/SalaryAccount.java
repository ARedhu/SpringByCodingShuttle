package com.codingShuttle.Ashish.Caching_Concurrency.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SalaryAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private BigDecimal balance;

    @Version
    private Long version; // @Version is added for optimistic locking. It protects our SalaryAccount from lost updates when multiple transactions modify it at the same time.
    // e.g. account has 50k.
    // p1, p2 both read the amount as 50k when version was 5.
    // p1 -> 50k - 10k = 40k, when version is 5, and change the version to 6.
    // p2 -> 50k - 20k = 30k, when version is 5, but version has already become 6. So, this trasaction will fail.
    // Because if it doesn't fail then the amount in the account will be 30k. But it should be either 40k or 20k.

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Employee employee;
}

