package com.example.expense.ExpenseManagement.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int expenseId;
    
    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private Date date;
    private String description;
    

    @ManyToOne
    @JoinColumn(referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(referencedColumnName = "categoryId", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(referencedColumnName = "currencyId", nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(referencedColumnName = "paymentMethodId", nullable = false)
    private PaymentMethod paymentMethod;

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
}