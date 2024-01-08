package com.example.expense.ExpenseManagement.Service;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.expense.ExpenseManagement.DTO.BudgetDto;
import com.example.expense.ExpenseManagement.Entity.Budget;
import com.example.expense.ExpenseManagement.ExceptionController.ApplicationException;
import com.example.expense.ExpenseManagement.ExceptionController.ValidationException;
import com.example.expense.ExpenseManagement.Repository.BudgetRepository;
import com.example.expense.ExpenseManagement.Repository.UserRepository;

@Service
public class BudgetService {
    
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper mapper;
    
    public List<BudgetDto> getAllBudgets(int userId) {

        List<Budget> budgetList = null;
        List<BudgetDto> budgetDtoList = new ArrayList<>();

        try {
            budgetList = budgetRepository.findBudgetsByUserId(userId);
            if(budgetList == null) {
                throw new ValidationException("No record found for the given userId", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected Error occurred while retrieving");
        }

        for(Budget budget : budgetList) {
            budgetDtoList.add(this.mapBudgetToBudgetDto(budget));
        }
        return budgetDtoList;
    }

    public BudgetDto getBudgetById(int budgetId) {

        Budget budget = null;
        try {
            budget = budgetRepository.findByBudgetId(budgetId);
            if(budget == null)
                throw new ValidationException("There is no record for the given budgetId.", HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            throw new ApplicationException("An unexpected Error occurred while retrieving");
        }
        
        return this.mapBudgetToBudgetDto(budget);
    }

    public void addBudget(BudgetDto budgetDto) {
        
        if((budgetDto == null) || (budgetDto.getBudgetType() == null)  ||
            (budgetDto.getBudgetAmount() == 0.0) || (budgetDto.getUserId() == 0)){

                throw new ValidationException("Field value must not be empty", HttpStatus.BAD_REQUEST);
        }

        budgetRepository.save(this.mapBudgetDtoToBudget(budgetDto));
        
    }

    public void updateBudget(BudgetDto updatedBudgetDto){

        Budget existingBudget = null;
        try{
            existingBudget = budgetRepository.findByBudgetId(updatedBudgetDto.getBudgetId());

            if(updatedBudgetDto.getBudgetType() != null)
                existingBudget.setBudgetType(updatedBudgetDto.getBudgetType());

            if(updatedBudgetDto.getBudgetAmount()!= 0.0)
                existingBudget.setBudgetAmount(updatedBudgetDto.getBudgetAmount());
        }
        catch(Exception e){
            throw new ApplicationException("An unexpected error occured");
        }
        
        budgetRepository.save(existingBudget);
    }

    public BudgetDto mapBudgetToBudgetDto(Budget budget) {

        BudgetDto budgetDto = mapper.map(budget, BudgetDto.class);
        budgetDto.setUserId(budget.getUser().getUserId());
        return budgetDto;
    }

    public Budget mapBudgetDtoToBudget(BudgetDto budgetDto) {

        Budget budget = mapper.map(budgetDto, Budget.class);
        budget.setUser(userRepository.findByUserId(budgetDto.getUserId()));
        return budget;
    }

}
