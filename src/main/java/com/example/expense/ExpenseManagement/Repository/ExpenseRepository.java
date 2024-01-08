package com.example.expense.ExpenseManagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.expense.ExpenseManagement.Entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    
    @Query("Select e from Expense e inner join e.user u where u.userId = :userId")
    List<Expense> findByUserId(@Param("userId") int userd);

    @Query("Select e from Expense e where e.expenseId = :expenseId")
    Expense findByExpenseId(@Param("expenseId") int expenseId);



    @Query("Select e from Expense e inner join e.category c where c.categoryName = :categoryName")
    List<Expense> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("Select e from Expense e inner join e.category c group by c.categoryName")
    List<Expense> findByCategory();

    @Query("Select e from Expense e inner join e.currency c where c.currencyCode = :currencyCode")
    List<Expense> findByCurrencyCode(@Param("currencyCode") String currencyCode);

    @Query("Select e from Expense e inner join e.paymentMethod p where p.paymentMethodName = :paymentMethodName")
    List<Expense> findByPaymentMethodName(@Param("paymentMethodName") String paymentMethodName);

    @Query("Select e from Expense e order by e.amount desc")
    List<Expense> findByAmount();

    @Query("Select e from Expense e order by e.date desc")
    List<Expense> findByDate();

}