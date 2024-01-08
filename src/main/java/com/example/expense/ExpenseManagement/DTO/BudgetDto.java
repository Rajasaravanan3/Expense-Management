package com.example.expense.ExpenseManagement.DTO;

public class BudgetDto {

    private int budgetId;
    private String budgetType;
    private double budgetAmount;
    private int userId;

    public BudgetDto(){}

    public BudgetDto(int budgetId, String budgetType, double budgetAmount, int userId) {
        
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

    public String getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(String budgetType) {
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
