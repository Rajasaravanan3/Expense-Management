package com.example.expense.ExpenseManagement.Service;

import java.util.List;

import org.apache.el.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.expense.ExpenseManagement.Entity.PaymentMethod;
import com.example.expense.ExpenseManagement.ExceptionController.ApplicationException;
import com.example.expense.ExpenseManagement.ExceptionController.ValidationException;
import com.example.expense.ExpenseManagement.Repository.PaymentMethodRepository;

@Service
public class PaymentMethodService {
    
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethod> getAllPaymentMethods(){

        List<PaymentMethod> paymentMethodList = null;
        try{
            paymentMethodList = paymentMethodRepository.findAll();
            if(paymentMethodList == null) {
                throw new ValidationException("No record found", HttpStatus.NOT_FOUND);
        }
        }
        catch(ValidationException exception) {
            throw exception;
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while retrieving the payment methods");
        }
        return paymentMethodList;
    }

    public PaymentMethod findPaymentMethodById(int paymentMethodId) {
        PaymentMethod paymentMethod = null;

        try {
            paymentMethod = paymentMethodRepository.findByPaymentMethodId(paymentMethodId);
            if(paymentMethod == null) {
                throw new ValidationException("No record found for the given paymentMethodId", HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while retrieving the payment method");
        }
        return paymentMethod;
    }

    public PaymentMethod findPaymentMethodByName(String paymentMethodName) {
        PaymentMethod paymentMethod = null;

        try {
            paymentMethod = paymentMethodRepository.findByPaymentMethodName(paymentMethodName);
            if(paymentMethod == null) {
                throw new ValidationException("No record found for the given paymentMethodId", HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while retrieving the payment method");
        }
        return paymentMethod;
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {

        try {
            if(paymentMethod == null || 
                (paymentMethod.getPaymentMethodName() instanceof String && paymentMethod.getPaymentMethodName().isEmpty()))
                
                    throw new ValidationException("Non nullable Field value must not be empty", HttpStatus.BAD_REQUEST);
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occurred while saving");
        }

        paymentMethodRepository.save(paymentMethod);
    }

    public void deletePaymentMethodById(int paymentMethodId) {
        paymentMethodRepository.deleteById(paymentMethodId);
    }

    public void updatePaymentMethod(PaymentMethod updatedPaymentMethod) {

        PaymentMethod existingPaymentMethod = null;

        try {
            existingPaymentMethod = paymentMethodRepository.findByPaymentMethodId(updatedPaymentMethod.getPaymentMethodId());

            if(updatedPaymentMethod.getPaymentMethodName() instanceof String && ! (updatedPaymentMethod.getPaymentMethodName().isEmpty()))
                existingPaymentMethod.setPaymentMethodName(updatedPaymentMethod.getPaymentMethodName());
            
            if(updatedPaymentMethod.getDescription() instanceof String && ! (updatedPaymentMethod.getDescription().isEmpty()))
                existingPaymentMethod.setDescription(updatedPaymentMethod.getDescription());
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while updating");
        }
        paymentMethodRepository.save(existingPaymentMethod);
    }

}
