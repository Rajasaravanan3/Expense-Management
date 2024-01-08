package com.example.expense.ExpenseManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense.ExpenseManagement.DTO.ExpenseDto;
import com.example.expense.ExpenseManagement.Service.CategoryService;
import com.example.expense.ExpenseManagement.Service.CurrencyService;
import com.example.expense.ExpenseManagement.Service.ExpenseService;
import com.example.expense.ExpenseManagement.Service.PaymentMethodService;
import com.example.expense.ExpenseManagement.Service.UserService;

@RestController
@RequestMapping("/expenses")
@CrossOrigin
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CurrencyService currencyService;

    private PaymentMethodService paymentMethodService;

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getExpensesBYUserId(@RequestParam("userId") int userId){

        List<ExpenseDto> expenseDtoList = expenseService.getAllExpensesByUserId(userId);
        if(expenseDtoList != null && userService.getUserById(userId) != null) {
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("expenseId") int expenseId){

        ExpenseDto expenseDto = expenseService.getExpenseById(expenseId);
        if(expenseDto != null) {
            return new ResponseEntity<>(expenseDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addExpense(@RequestBody ExpenseDto expenseDto) {
        
        return new ResponseEntity<>(expenseService.addExpense(expenseDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable int expenseId) {

        if(expenseService.getExpenseById(expenseId) != null ) {
            expenseService.deleteExpenseById(expenseId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Void> updateExpense(@RequestBody ExpenseDto updatedExpenseDto) {

        if(expenseService.getExpenseById(updatedExpenseDto.getExpenseId()) != null && userService.getUserById(updatedExpenseDto.getUserId()) != null) {
            expenseService.updateExpense(updatedExpenseDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //error
    @GetMapping("/category")
    public ResponseEntity<List<ExpenseDto>> getByCategory() {

        List<ExpenseDto> expenseDtoList = expenseService.getByCategory();
        if(expenseDtoList != null) {
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/amount-spent")
    public ResponseEntity<List<ExpenseDto>> getByAmountSpentHigherToLower() {

        List<ExpenseDto> expenseDtoList = expenseService.getByAmountSpentHigherToLower();
        if(expenseDtoList != null) {
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/recent-expenses")
    public ResponseEntity<List<ExpenseDto>> getByRecentExpenses() {

        List<ExpenseDto> expenseDtoList = expenseService.getByRecentExpenses();
        if(expenseDtoList != null) {
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //error
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ExpenseDto>> getByCategoryName(@PathVariable("categoryName") String categoryName) {
        
        List<ExpenseDto> expenseDtoList = expenseService.getByCategoryName(categoryName);
        if(expenseDtoList != null && categoryService.getCategoryByName(categoryName) != null) {
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/currency/{currencyCode}")
    public ResponseEntity<List<ExpenseDto>> getByCurrencyCode(@PathVariable("currencyCode") String currencyCode) {

        List<ExpenseDto> expenseDtoList = expenseService.getByCurrencyCode(currencyCode);
        if(expenseDtoList != null && currencyService.displayCurrencyByCode(currencyCode) != null) {
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //error
    @GetMapping("/payment/{paymentMethodName}")
    public ResponseEntity<List<ExpenseDto>> getByPaymentMethodName(@PathVariable("paymentMethodName") String paymentMethodName) {

        List<ExpenseDto> expenseDtoList = expenseService.getByPaymentMethodname(paymentMethodName);
        if(expenseDtoList != null && paymentMethodService.findPaymentMethodByName(paymentMethodName) != null) {
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
