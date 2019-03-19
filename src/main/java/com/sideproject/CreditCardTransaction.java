package com.sideproject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;
import java.util.UUID;

@Data
@Entity
public class CreditCardTransaction {

    @Id
    @GeneratedValue
    private UUID id;
    private String vendor;
    private String description;
    private double amount;
    private Date date;
    private BudgetCategory budgetCategory;

    private CreditCardTransaction() {
    }

    public CreditCardTransaction(Date date, String vendor, String description, double amount, BudgetCategory budgetCategory) {
        this.vendor = vendor;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.budgetCategory = budgetCategory;
    }
}
