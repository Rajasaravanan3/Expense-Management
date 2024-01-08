package com.example.expense.ExpenseManagement.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.expense.ExpenseManagement.Entity.Currency;


public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    @Query("Select c from Currency c where c.currencyId = :currencyId")
    Currency findByCurrencyId(@Param("currencyId") int currencyId);

    @Query("Select c from Currency c where c.currencyCode = :currencyCode")
    Currency findByCurrencyCode(@Param("currencyCode") String currencyCode);

    // @Query("Select * from Currency")
    List<Currency> findAll();
}
