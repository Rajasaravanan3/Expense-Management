package com.example.expense.ExpenseManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense.ExpenseManagement.Entity.Currency;
import com.example.expense.ExpenseManagement.Service.CurrencyService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/currencies")
public class CurrencyController {
    
    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<Currency>> getCurrencies(){

        List<Currency> currencies = currencyService.getALlCurrencies();
        if(currencies != null){
            return new ResponseEntity<>(currencies, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{currencyId}")
    public ResponseEntity<Currency> viewCurrencyById(@PathVariable("currencyId") int currencyId) {

        Currency currency = currencyService.displayCurrencyById(currencyId);
        if(currency != null){
            return new ResponseEntity<>(currency, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/currencyCode/{currencyCode}")
    public ResponseEntity<Currency> viewCurrencyByCode(@PathVariable String currencyCode) {
        
        Currency currency = currencyService.displayCurrencyByCode(currencyCode);
        if(currency != null){
            return new ResponseEntity<>(currency, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> addCurrency(@RequestBody Currency currency){
        currencyService.addCurrency(currency);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{currencyId}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable int currencyId) {

        if(currencyService.displayCurrencyById(currencyId)!= null) {
            currencyService.deleteCurrencyById(currencyId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Void> updateCurrency(@RequestBody Currency updatedCurrency){

        if(currencyService.displayCurrencyById(updatedCurrency.getCurrencyId()) != null) {
            currencyService.updateCurrency(updatedCurrency);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
