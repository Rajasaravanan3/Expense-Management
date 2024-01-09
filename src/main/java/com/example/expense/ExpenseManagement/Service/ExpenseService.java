package com.example.expense.ExpenseManagement.Service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.expense.ExpenseManagement.DTO.ExpenseDto;
import com.example.expense.ExpenseManagement.Entity.Budget;
import com.example.expense.ExpenseManagement.Entity.Expense;
import com.example.expense.ExpenseManagement.Repository.BudgetRepository;
import com.example.expense.ExpenseManagement.Repository.CategoryRepository;
import com.example.expense.ExpenseManagement.Repository.CurrencyRepository;
import com.example.expense.ExpenseManagement.Repository.ExpenseRepository;
import com.example.expense.ExpenseManagement.Repository.PaymentMethodRepository;
import com.example.expense.ExpenseManagement.Repository.UserRepository;
import com.example.expense.ExpenseManagement.ExceptionController.ApplicationException;
import com.example.expense.ExpenseManagement.ExceptionController.ValidationException;

@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    
    @Autowired
    private DozerBeanMapper mapper;

    private static final String EMPTY_RECORD = "No record found";
    private static final String ERROR_MESSAGE = "An unexpected error occured while retrieving the expense";


    public List<ExpenseDto> getAllExpensesByUserId(int userId){

        List<Expense> expenseList = null;
        List<ExpenseDto> expenseDtoList = new ArrayList<>();
        
        try {
            expenseList = expenseRepository.findByUserId(userId);
            if(expenseList == null) {
                throw new ValidationException("No record found for the user " + userId, HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException("An error occurred while getting the expense list for the user "+ userId);
        }

        for(Expense expense : expenseList) {
            expenseDtoList.add(this.mapExpenseToExpenseDto(expense));
        }
        return expenseDtoList;
    }

    public ExpenseDto getExpenseById(int expenseId){
        Expense expense = null;
        try {
            expense = expenseRepository.findByExpenseId(expenseId);
            if(expense == null) {
                throw new ValidationException("No record found for the expenseId " + expenseId, HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException(ERROR_MESSAGE);
        }
        return this.mapExpenseToExpenseDto(expense);
    }

    public String addExpense(ExpenseDto expenseDto) {

        String returnMessage = "";
        try {
            if(expenseDto == null || expenseDto.getAmount() == 0.0 || (expenseDto.getDate() == null || expenseDto.getDate() == null) || 
                expenseDto.getCategoryId() == 0 || expenseDto.getCurrencyId() == 0 || expenseDto.getPaymentMethodId() == 0 || 
                expenseDto.getUserId() == 0) {

                    throw new ValidationException("Non nullable Field value must not be empty", HttpStatus.BAD_REQUEST);
            }
            
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occurred while adding expense");
        }

        expenseRepository.save(this.mapExpenseDtoToExpense(expenseDto));
        return returnMessage;   //if string is not empty then display message as a notification
    }

    public void deleteExpenseById(int expenseId) {
        expenseRepository.deleteById(expenseId);
    }

    public void updateExpense(ExpenseDto updatedExpenseDto) {

        Expense existingExpense  = null;
        Expense updatedExpense = null;

        try {
            existingExpense = expenseRepository.findByExpenseId(updatedExpenseDto.getExpenseId());
            if(existingExpense == null) {
                throw new ValidationException("No record found for the expenseId to update", HttpStatus.NOT_FOUND);
            }
            updatedExpense = this.mapExpenseDtoToExpense(updatedExpenseDto);

            if(updatedExpense.getAmount() != 0.0)
                existingExpense.setAmount(updatedExpense.getAmount());

            if(updatedExpense.getDate() != null)
                existingExpense.setDate(updatedExpense.getDate());

            if(updatedExpense.getDescription() instanceof String && updatedExpense.getDescription().isEmpty())
                existingExpense.setDescription(updatedExpense.getDescription());

            if(updatedExpense.getUser() != null)
                existingExpense.setUser(updatedExpense.getUser());

            if(updatedExpense.getCategory() != null)
                existingExpense.setCategory(updatedExpense.getCategory());

            if(updatedExpense.getCurrency() != null)
                existingExpense.setCurrency(updatedExpense.getCurrency());

            if(updatedExpense.getPaymentMethod() != null)
                existingExpense.setPaymentMethod(updatedExpense.getPaymentMethod());
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected error occured while updating the expense");
        }
        expenseRepository.save(existingExpense);
    }


    public List<ExpenseDto> getByAmountSpentHigherToLower() {

        List<Expense> expenseList = null;
        List<ExpenseDto> expenseDtoList = new ArrayList<>();

        try {
            expenseList = expenseRepository.findByAmount();
            if(expenseList == null) {
                throw new ValidationException(EMPTY_RECORD, HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException(ERROR_MESSAGE);
        }
        
        for(Expense expense : expenseList) {
            expenseDtoList.add(this.mapExpenseToExpenseDto(expense));
        }
        return expenseDtoList;
    }

    public List<ExpenseDto> getByRecentExpenses() {

        List<Expense> expenseList = null;
        List<ExpenseDto> expenseDtoList = new ArrayList<>();

        try {
            expenseList = expenseRepository.findByDate();
            if(expenseList == null) {
                throw new ValidationException(EMPTY_RECORD, HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException(ERROR_MESSAGE);
        }
        
        for(Expense expense : expenseList) {
            expenseDtoList.add(this.mapExpenseToExpenseDto(expense));
        }
        return expenseDtoList;
    }

    public List<ExpenseDto> getByCategoryName(String categoryName) {

        List<Expense> expenseList = null;
        List<ExpenseDto> expenseDtoList = new ArrayList<>();

        try {
            expenseList = expenseRepository.findByCategoryName(categoryName);
            if(expenseList == null) {
                throw new ValidationException(EMPTY_RECORD, HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException(ERROR_MESSAGE);
        }
        
        for(Expense expense : expenseList) {
            expenseDtoList.add(this.mapExpenseToExpenseDto(expense));
        }
        return expenseDtoList;
    }

    public List<ExpenseDto> getByCurrencyCode(String currencyCode) {

        List<Expense> expenseList = null;
        List<ExpenseDto> expenseDtoList = new ArrayList<>();

        try {
            expenseList = expenseRepository.findByCurrencyCode(currencyCode);
            if(expenseList == null) {
                throw new ValidationException(EMPTY_RECORD, HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException(ERROR_MESSAGE);
        }
        
        for(Expense expense : expenseList) {
            expenseDtoList.add(this.mapExpenseToExpenseDto(expense));
        }
        return expenseDtoList;
    }

    public List<ExpenseDto> getByPaymentMethodname(String paymentMethodName) {

        List<Expense> expenseList = null;
        List<ExpenseDto> expenseDtoList = new ArrayList<>();
        
        try {
            expenseList = expenseRepository.findByPaymentMethodName(paymentMethodName);
            if(expenseList == null) {
                throw new ValidationException(EMPTY_RECORD, HttpStatus.NOT_FOUND);
            }
        }
        catch(ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ApplicationException(ERROR_MESSAGE);
        }
        
        for(Expense expense : expenseList) {
            expenseDtoList.add(this.mapExpenseToExpenseDto(expense));
        }
        return expenseDtoList;
    }

    public ExpenseDto mapExpenseToExpenseDto(Expense expense) {

        ExpenseDto expenseDto = mapper.map(expense, ExpenseDto.class);
        expenseDto.setUserId(expense.getUser().getUserId());
        expenseDto.setCategoryId(expense.getCategory().getCategoryId());
        expenseDto.setCurrencyId(expense.getCurrency().getCurrencyId());
        expenseDto.setPaymentMethodId(expense.getPaymentMethod().getPaymentMethodId());
        return expenseDto;
    }

    public Expense mapExpenseDtoToExpense(ExpenseDto expenseDto) {

        Expense expense = mapper.map(expenseDto, Expense.class);
        expense.setUser(userRepository.findByUserId(expenseDto.getUserId()));
        expense.setCategory(categoryRepository.findByCategoryId(expenseDto.getCategoryId()));
        expense.setCurrency(currencyRepository.findByCurrencyId(expenseDto.getCurrencyId()));
        expense.setPaymentMethod(paymentMethodRepository.findByPaymentMethodId(expenseDto.getPaymentMethodId()));
        return expense;
    }

}
