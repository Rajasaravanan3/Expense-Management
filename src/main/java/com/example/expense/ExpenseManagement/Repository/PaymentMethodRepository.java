package com.example.expense.ExpenseManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.expense.ExpenseManagement.Entity.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

    @Query("Select p from PaymentMethod p where paymentMethodId = :paymentMethodId")
    PaymentMethod findByPaymentMethodId(@Param("paymentMethodId") int paymentMethodId);

    @Query("Select p from PaymentMethod p where p.paymentMethodName = :paymentMethodName")
    PaymentMethod findByPaymentMethodName(@Param("paymentMethodName") String paymentMethodName);
}
