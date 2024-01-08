package com.example.expense.ExpenseManagement.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.expense.ExpenseManagement.Entity.Currency;
import com.example.expense.ExpenseManagement.ExceptionController.ApplicationException;
import com.example.expense.ExpenseManagement.ExceptionController.ValidationException;
import com.example.expense.ExpenseManagement.Repository.CurrencyRepository;

@Service
public class CurrencyService {
    
    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> getALlCurrencies(){
        return currencyRepository.findAll();
    }

    public Currency displayCurrencyById(int currencyId){

        Currency currency = null;

        try {
            currency = currencyRepository.findByCurrencyId(currencyId);

            if(currency == null) {
                throw new ValidationException("No record found for the given currencyId", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while retrieving the currency");
        }

        return currency;
    }

    public Currency displayCurrencyByCode(String currencyCode){

        Currency currency = null;

        try {
            currency = currencyRepository.findByCurrencyCode(currencyCode);

            if(currency == null) {
                throw new ValidationException("No record found for the given currencyId", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while retrieving the currency");
        }

        return currency;
    }

    public void addCurrency(Currency currency){

        try {
            if(currency == null || currency.getCurrencyId() == 0 || 
            (currency.getCurrencyCode() instanceof String && currency.getCurrencyCode().isEmpty()))
                throw new ValidationException("Non nullable Field value must not be empty", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occurred while saving");
        }
        currencyRepository.save(currency);
    }

    public void deleteCurrencyById(int currencyId){
        currencyRepository.deleteById(currencyId);
    }

    public void updateCurrency(Currency updatedCurrency){
        Currency existingCurrency = null;

        try {
            existingCurrency = currencyRepository.findByCurrencyId(updatedCurrency.getCurrencyId());

            if(updatedCurrency.getCurrencyCode() instanceof String && ! (updatedCurrency.getCurrencyCode().isEmpty()))
                existingCurrency.setCurrencyCode(updatedCurrency.getCurrencyCode());

            if(updatedCurrency.getCurrencyName() instanceof String &&! (updatedCurrency.getCurrencyName().isEmpty()))
                existingCurrency.setCurrencyName(updatedCurrency.getCurrencyName());
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while updating");
        }

        currencyRepository.save(existingCurrency);
    }

}
