package com.example.expense.ExpenseManagement.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Budget {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int budgetId;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BudgetType budgetType;

    @Column(nullable = false)
    private double budgetAmount;

    @ManyToOne
    @JoinColumn(referencedColumnName = "userId", nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public BudgetType getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(BudgetType budgetType) {
        this.budgetType = budgetType;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
    
    public Budget(){}

    public Budget(int budgetId, BudgetType budgetType, double budgetAmount) {
        this.budgetId = budgetId;
        this.budgetType = budgetType;
        this.budgetAmount = budgetAmount;
    }

}
