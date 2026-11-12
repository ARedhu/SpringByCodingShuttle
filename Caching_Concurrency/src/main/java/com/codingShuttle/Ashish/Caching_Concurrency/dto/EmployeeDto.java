package com.codingShuttle.Ashish.Caching_Concurrency.dto;

import lombok.Data;

import java.io.Serializable;

// We implemented it with Serializable so that it's objects can be converted to a byte stream which various cache providers need like Redis. We need it when we want to use some external cache provider other than JVM or program in-memory and definitely we will use that external provider. But again later we will convert that byte stream to JSON which is more secure.
@Data
public class EmployeeDto implements Serializable {
    private Long id;
    private String email;
    private String name;
    private Long salary;
}