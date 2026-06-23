package com.codingShuttle.jpaTutorials.jpaTuts.dto;

import java.math.BigDecimal;

public class CProductDTOClass {
    private final String outcome;
    public CProductDTOClass(String title, BigDecimal price){
        this.outcome = title + " = " + price;
    }
    public String getOutcome() {
        return outcome;
    }
}
