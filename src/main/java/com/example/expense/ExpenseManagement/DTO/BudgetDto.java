package com.example.expense.ExpenseManagement.DTO;

import com.example.expense.ExpenseManagement.Entity.BudgetType;

public class BudgetDto {

    private int budgetId;
    private BudgetType budgetType;
    private double budgetAmount;
    private int userId;

    public BudgetDto(){}

    public BudgetDto(int budgetId, BudgetType budgetType, double budgetAmount, int userId) {
        
        this.budgetId = budgetId;
        this.budgetType = budgetType;
        this.budgetAmount = budgetAmount;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
