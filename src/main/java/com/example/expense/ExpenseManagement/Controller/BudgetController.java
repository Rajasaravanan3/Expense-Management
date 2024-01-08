package com.example.expense.ExpenseManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.expense.ExpenseManagement.Service.BudgetService;
import com.example.expense.ExpenseManagement.Service.UserService;
import com.example.expense.ExpenseManagement.DTO.BudgetDto;


@RestController
@RequestMapping("/budgets")
@CrossOrigin
public class BudgetController {
    
    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<BudgetDto>> getBudgets(@RequestParam("userId") int userId){

        List<BudgetDto> budgetDto = budgetService.getAllBudgets(userId);
        if(budgetDto != null && userService.getUserById(userId) != null) {
            return new ResponseEntity<>(budgetDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{budgetId}")
    public ResponseEntity<BudgetDto> getBudgetsByBudgetId(@PathVariable("budgetId") int budgetId){

        BudgetDto budgetDto = budgetService.getBudgetById(budgetId);

        if(budgetDto != null) {
            return new ResponseEntity<>(budgetDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> addBudget(@RequestBody BudgetDto budgetDto) {
        budgetService.addBudget(budgetDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateBudget(@RequestBody BudgetDto updatedBudgetDto) {

        if(budgetService.getBudgetById(updatedBudgetDto.getBudgetId()) != null && userService.getUserById(updatedBudgetDto.getUserId()) != null) {
            budgetService.updateBudget(updatedBudgetDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
}
