package com.example.expense.ExpenseManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.example.expense.ExpenseManagement.Entity.Budget;

// @Repositoy is not necessary as JpaRepository carries already
public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    
    @Query("Select b from Budget b where b.budgetId = :budgetId")
    Budget findByBudgetId(@Param("budgetId") int budgetId);

    @Query("Select b from Budget b inner join b.user u where u.userId = :userId")
    List<Budget> findBudgetsByUserId(@Param("userId") int userId);

}

