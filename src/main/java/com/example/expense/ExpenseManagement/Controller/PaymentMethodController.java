package com.example.expense.ExpenseManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense.ExpenseManagement.Service.PaymentMethodService;
import com.example.expense.ExpenseManagement.Entity.PaymentMethod;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/paymentMethods")
public class PaymentMethodController {
    
    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping
    public ResponseEntity<List<PaymentMethod>> getPaymentMethods() {

        List<PaymentMethod> paymentMethodList = paymentMethodService.getAllPaymentMethods();
        if(!paymentMethodList.isEmpty()){
            return new ResponseEntity<>(paymentMethodList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> getPaymentMethodById(@PathVariable("paymentMethodId") int paymentMethodId) {

        PaymentMethod paymentMethod = paymentMethodService.findPaymentMethodById(paymentMethodId);
        if(paymentMethod != null) {
            return new  ResponseEntity<>(paymentMethod, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> addPaymentMethod(@RequestBody PaymentMethod paymentMethod) {

        paymentMethodService.addPaymentMethod(paymentMethod);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{paymentMethodId}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable("paymentMethodId") int paymentMethodId) {

        if(paymentMethodService.findPaymentMethodById(paymentMethodId) != null) {
            paymentMethodService.deletePaymentMethodById(paymentMethodId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Void> updatePaymentMethod(@RequestBody PaymentMethod updatedPaymentMethod) {
        
        if(paymentMethodService.findPaymentMethodById(updatedPaymentMethod.getPaymentMethodId()) != null) {
            paymentMethodService.updatePaymentMethod(updatedPaymentMethod);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
